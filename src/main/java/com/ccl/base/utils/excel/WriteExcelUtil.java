package com.ccl.base.utils.excel;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 
 * Excel 导入工具类
 *
 */
public class WriteExcelUtil {

	private static final Logger logger = LoggerFactory.getLogger(WriteExcelUtil.class);

	/**
	 * 
	 * @param heads
	 *            头信息 标题栏
	 * @param list
	 *            数据
	 * @return
	 * @throws Exception
	 */
	public static InputStream exp(String[] heads, List<String[]> list) throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		// 创建一个xls文件
		WritableWorkbook workbook = Workbook.createWorkbook(outputStream);
		// 创建xls文件里面的表
		WritableSheet sheet = workbook.createSheet("sheet1", 0);
		// param1:col 列
		// param2:row 行
		for (int i = 0; i < heads.length; i++) {
			Label label = new Label(i, 0, heads[i]);
			sheet.addCell(label);
			// sheet.setColumnView(i, heads[i].length());
		}
		// 2.处理数据
		for (int i = 0; i < list.size(); i++) {
			String[] strings = list.get(i);// 一行的数据
			for (int j = 0; j < strings.length; j++) {
				Label label = new Label(j, i + 1, strings[j]);// 排除表头已经用了一行
				sheet.addCell(label);
				if (strings[j] != null) {
					sheet.setColumnView(j, strings[j].length() + 10);
				}
			}
		}
		// 关闭流
		workbook.write();
		workbook.close();
		return new ByteArrayInputStream(outputStream.toByteArray());
	}

	/**
	 * 设置请求头为zip文件下载
	 * 
	 * @MethodName：setResponseHeader
	 * @param response
	 * @param fileName
	 * @ReturnType：void @Description：
	 * @Creator：chenchuanliang
	 * @CreateTime：2017年5月9日下午5:25:36 @Modifier： @ModifyTime：
	 */
	public static void setResponseHeader(HttpServletResponse response, String fileName) {
		try {
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
			// "attachment;filename=" + java.net.URLEncoder.encode(fileName,
			// "UTF-8") + ".zip");
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * excel导出
	 * 
	 * @MethodName：toExcelZip
	 * @param list
	 *            需要导出的数据
	 * @param head
	 *            需要导出的数据的列标题
	 * @param request
	 * @param response
	 * @param length
	 *            每个xlsx文件希望存储的行数
	 * @param fileName
	 *            希望保存的文件名称(不需要添加后缀)
	 * @throws IOException
	 * @ReturnType：void @Description：
	 * @Creator：chenchuanliang
	 * @CreateTime：2017年5月9日下午5:25:50 @Modifier： @ModifyTime：
	 */
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public static void toExcelZip(List list, List<String> head, HttpServletRequest request,
			HttpServletResponse response, int length, String fileName) throws IOException {

		if (CollectionUtils.isEmpty(list)) {
			throw new NullPointerException();
		}
		Class<?> clz = list.get(0).getClass();
		Field[] fields = clz.getDeclaredFields();// 获取当前类的field
		// 存储需要导出的列
		List<Field> excelFields = new ArrayList<>();
		for (Field field : fields) {
			if (!"serialVersionUID".equals(field.getName())) {
				// if (field.isAnnotationPresent(ExcelAttribute.class))
				// {//通过注解获取需要导出的列
				excelFields.add(field);
				// }
			}
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		setResponseHeader(response, fileName);
		OutputStream out = response.getOutputStream();
		String separator = File.separator;
		List<String> fileNames = new ArrayList<>();// 存储每一个xlsx的文件名, 供zip打包获取文件
		File zip = new File(request.getRealPath(separator + "files") + separator + fileName + ".zip");// 生成zip包文件路径

		for (int j = 0, n = list.size() / (length + 1); j <= n; j++) {
			SXSSFWorkbook book = new SXSSFWorkbook();
			Sheet sheet = book.createSheet("sheet");

			String file = request.getRealPath(separator + "files") + separator + fileName + "_" + (j + 1) + ".xlsx";
			File file2 = new File(file);
			File parentFile = file2.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			file2.createNewFile();

			fileNames.add(file);
			FileOutputStream o = null;
			try {
				o = new FileOutputStream(file);
				Row row = sheet.createRow(0);
				for (int k = 0; k < head.size(); k++) {
					Cell cell = row.createCell(k);
					cell.setCellValue(head.get(k));
				}
				// for (int k = 0; k <excelFields.size(); k ++) {//获取注解上的列名
				// Field field = excelFields.get(k);
				// ExcelAttribute annotation =
				// field.getAnnotation(ExcelAttribute.class);
				// Cell cell = row.createCell(k);
				// cell.setCellValue(annotation.name());
				// }

				for (int i = 1, min = (list.size() - j * length + 1) > (length + 1) ? (length + 1)
						: (list.size() - j * length + 1); i < min; i++) {
					Object object = list.get(length * (j) + i - 1);
					row = sheet.createRow(i);
					for (int k = 0; k < excelFields.size(); k++) {
						Field field = excelFields.get(k);
						Method declaredMethod = clz.getDeclaredMethod(
								"get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
						Object value = declaredMethod.invoke(object);
						Cell cell = row.createCell(k);
						if (value == null) {
							cell.setCellValue("");
						} else {
							if (value instanceof Date) {
								cell.setCellValue(sdf.format((Date) value));
							} else {
								cell.setCellValue(value.toString());
							}
						}
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				book.write(o);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				o.flush();
				o.close();
			}
		}
		File srcfile[] = new File[fileNames.size()];
		for (int i = 0, n = fileNames.size(); i < n; i++) {
			srcfile[i] = new File(fileNames.get(i));
		}
		logger.error("压缩前-----------------------------------------------------------------");
		zipFiles(srcfile, zip);
		logger.error("压缩后-----------------------------------------------------------------");
		FileInputStream inStream = new FileInputStream(zip);
		byte[] buf = new byte[4096];
		int readLength;
		while (((readLength = inStream.read(buf)) != -1)) {
			out.write(buf, 0, readLength);
		}
		inStream.close();
		logger.error("处理完毕, 返回数据到response的流中-----------------------------------------------------------------");
	}

	/**
	 * excel导出
	 * 
	 * @MethodName：toExcelZip
	 * @param list
	 *            需要导出的数据
	 * @param head
	 *            需要导出的数据的列标题
	 * @param request
	 * @param response
	 * @param fileName
	 *            希望保存的文件名称(不需要添加后缀)
	 * @throws IOException
	 * @ReturnType：void @Description：
	 * @Creator：chenchuanliang
	 * @CreateTime：2017年5月9日下午5:25:50 @Modifier： @ModifyTime：
	 */
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public static void toExcel(List list, List<String> head, HttpServletRequest request, HttpServletResponse response,
			String fileName) throws IOException {

		if (CollectionUtils.isEmpty(list)) {
			throw new NullPointerException();
		}
		Class<?> clz = list.get(0).getClass();
		Field[] fields = clz.getDeclaredFields();// 获取当前类的field
		// 存储需要导出的列
		List<Field> excelFields = new ArrayList<>();
		for (Field field : fields) {
			if (!"serialVersionUID".equals(field.getName())) {
				// if (field.isAnnotationPresent(ExcelAttribute.class))
				// {//通过注解获取需要导出的列
				excelFields.add(field);
				// }
			}
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		setResponseHeader(response, fileName);
		OutputStream out = response.getOutputStream();
		SXSSFWorkbook book = new SXSSFWorkbook();
		Sheet sheet = book.createSheet("sheet");

		try {
			Row row = sheet.createRow(0);
			for (int k = 0; k < head.size(); k++) {
				Cell cell = row.createCell(k);
				cell.setCellValue(head.get(k));
			}
			// for (int k = 0; k <excelFields.size(); k ++) {//获取注解上的列名
			// Field field = excelFields.get(k);
			// ExcelAttribute annotation =
			// field.getAnnotation(ExcelAttribute.class);
			// Cell cell = row.createCell(k);
			// cell.setCellValue(annotation.name());
			// }

			for (int i = 0; i < list.size(); i++) {
				Object object = list.get(i);
				row = sheet.createRow(i + 1);
				for (int k = 0; k < excelFields.size(); k++) {
					Field field = excelFields.get(k);
					Method declaredMethod = clz.getDeclaredMethod(
							"get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
					Object value = declaredMethod.invoke(object);
					Cell cell = row.createCell(k);
					if (value == null) {
						cell.setCellValue("");
					} else {
						if (value instanceof Date) {
							cell.setCellValue(sdf.format((Date) value));
						} else {
							cell.setCellValue(value.toString());
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			book.write(out);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.error("处理完毕, 返回数据到response的流中-----------------------------------------------------------------");
	}

	/**
	 * 
	 * @MethodName：toExcel
	 * @param list
	 * @param request
	 * @param response
	 * @param fileName
	 * @throws IOException
	 * @ReturnType：void
	 * @Description：注解方式,导出excel
	 * @Creator：zhengjing
	 * @CreateTime：2017年6月15日上午10:56:40 @Modifier： @ModifyTime：
	 */
	@SuppressWarnings("rawtypes")
	public static void toExcel(List list, HttpServletRequest request, HttpServletResponse response, String fileName)
			throws IOException {

		if (CollectionUtils.isEmpty(list)) {
			throw new NullPointerException();
		}
		Class<?> clz = list.get(0).getClass();
		Class<?> parentClz = clz.getSuperclass();
		Field[] fields = clz.getDeclaredFields(); // 获取当前类的field
		Field[] parentFields = parentClz.getDeclaredFields();// 获取当前类父类的field
		// 存储需要导出的列
		Map<Field, String> excelFields = new LinkedHashMap<Field, String>();
		for (Field field : parentFields) {
			// 通过注解获取需要导出的列
			if (field.isAnnotationPresent(ExcelAttribute.class)) {
				excelFields.put(field, "super");
			}
		}
		for (Field field : fields) {
			// 通过注解获取需要导出的列
			if (field.isAnnotationPresent(ExcelAttribute.class)) {
				excelFields.put(field, "son");
			}
		}
		// 排序
		// excelFields = sortMapByValue(excelFields);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		setResponseHeader(response, fileName);
		OutputStream out = response.getOutputStream();
		SXSSFWorkbook book = new SXSSFWorkbook();
		Sheet sheet = book.createSheet("sheet");

		try {
			Set<Field> keySet = excelFields.keySet();
			Row row = sheet.createRow(0);
			// 设置EXCEL表头
			int i = 0;
			for (Field field : keySet) {
				ExcelAttribute annotation = field.getAnnotation(ExcelAttribute.class);
				Cell cell = row.createCell(i);
				cell.setCellValue(annotation.headName());
				i++;
			}
			// EXCEL具体内容
			for (int j = 0; j < list.size(); j++) {
				Object object = list.get(j);
				row = sheet.createRow(j + 1);
				int k = 0;
				for (Field field : keySet) {
					Method declaredMethod = null;
					if ("super".equals(excelFields.get(field))) {
						declaredMethod = parentClz.getDeclaredMethod(
								"get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
					} else if ("son".equals(excelFields.get(field))) {
						declaredMethod = clz.getDeclaredMethod(
								"get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
					}
					Object value = declaredMethod.invoke(object);
					Cell cell = row.createCell(k);
					if (value == null) {
						cell.setCellValue("");
					} else {
						if (value instanceof Date) {
							cell.setCellValue(sdf.format((Date) value));
						} else {
							cell.setCellValue(value.toString());
						}
					}
					k++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			book.write(out);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.error("处理完毕, 返回数据到response的流中-----------------------------------------------------------------");
	}

	/**
	 * 
	 * @MethodName：sortMapByValue
	 * @param oriMap
	 * @return
	 * @ReturnType：Map<String,String> @Description：
	 * @Creator：zhengjing
	 * @CreateTime：2017年6月21日下午2:02:39 @Modifier： @ModifyTime：
	 */
	public static Map<Field, String> sortMapByValue(Map<Field, String> oriMap) {
		Map<Field, String> sortedMap = new LinkedHashMap<Field, String>();
		if (oriMap != null && !oriMap.isEmpty()) {
			List<Map.Entry<Field, String>> entryList = new ArrayList<Map.Entry<Field, String>>(oriMap.entrySet());
			Collections.sort(entryList, new Comparator<Map.Entry<Field, String>>() {
				@Override
				public int compare(Entry<Field, String> entry1, Entry<Field, String> entry2) {
					return 0;
				}
			});
			Iterator<Map.Entry<Field, String>> iter = entryList.iterator();
			Map.Entry<Field, String> tmpEntry = null;
			while (iter.hasNext()) {
				tmpEntry = iter.next();
				sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
			}
		}
		return sortedMap;
	}

	/**
	 * 文件打包成zip
	 * 
	 * @MethodName：ZipFiles
	 * @param srcfile
	 * @param zipFile
	 * @ReturnType：void @Description：
	 * @Creator：chenchuanliang
	 * @CreateTime：2017年5月9日下午5:24:49 @Modifier： @ModifyTime：
	 */
	private static void zipFiles(File[] srcfile, File zipFile) {
		byte[] buf = new byte[4096];
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
			for (int i = 0; i < srcfile.length; i++) {
				logger.error("压缩中, 准备压缩第" + i + "个文件:" + srcfile[i].getName()
						+ "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
				FileInputStream in = new FileInputStream(srcfile[i]);
				out.putNextEntry(new ZipEntry(srcfile[i].getName()));
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.closeEntry();
				in.close();
				logger.error("压缩中, 完成压缩第" + i + "个文件:" + srcfile[i].getName()
						+ "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @MethodName：getExcelDownLoadListParam
	 * @param taskName
	 *            任务名称
	 * @param totalCount
	 *            总条数
	 * @param pageSize
	 *            页面大小（分割值：一个Excel文件多少条）
	 * @return
	 * @ReturnType：List<ExcelDownLoadListParamBean>
	 * @Description：获取Excel分包参数列表
	 * @Creator：zhengjing
	 * @CreateTime：2017年5月20日上午10:30:16 @Modifier： @ModifyTime：
	 */
	public static List<ExcelDownLoadListParamBean> getExcelDownLoadListParam(String fileName, Integer totalCount,
			Integer pageSize) {
		if (totalCount > 0) {
			Integer totalPage = (totalCount - 1) / pageSize + 1;
			List<ExcelDownLoadListParamBean> list = new ArrayList<ExcelDownLoadListParamBean>();
			for (int i = 1; i <= totalPage; i++) {
				ExcelDownLoadListParamBean bean = new ExcelDownLoadListParamBean();
				bean.setPageNow(i); // 当前页
				if (pageSize != null) {
					bean.setPageSize(pageSize); // 页面大小
				}
				String myfileName = fileName + "_" + new SimpleDateFormat("yyyy.MM.dd").format(new Date()) + "_" + ""
						+ i;// 拼接文件名
				bean.setFileName(myfileName);
				if (i == 1) {
					if (totalCount < pageSize) {
						bean.setScope(1 + "~" + totalCount);
					} else {
						bean.setScope(1 + "~" + pageSize);
					}
				} else if (i == totalPage) {
					bean.setScope(pageSize * (i - 1) + 1 + "~" + totalCount);
				} else {
					bean.setScope(pageSize * (i - 1) + 1 + "~" + pageSize * i);
				}
				list.add(bean);
			}
			return list;
		}
		return null;
	}

	/** 
	* 方法名: importExcelData 
	* 方法描述: excel导入
	* param: @param upload
	* param: @return    
	* @return: List<String[]>      
	* 修改人:huyanqing
	* 修改时间：2017年11月6日
	* 修改内容:增加了对日期的判断与处理
	*/
	public static List<String[]> importExcelData(MultipartFile upload) {
		// 1.将上传文件中的数据解析出来--重新封装成List<String[]>，一行数据一个String[]
		// 首先要定义一个List<String[]>来接收解析出来的数据
		List<String[]> stringsList = new ArrayList<String[]>();
		// 通过上传的文件拿到输入流
		try {
			InputStream is = upload.getInputStream();
			// 创建一个上传的内存对象
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
			// 通过内存对象，获得表
			XSSFSheet sheet = xssfWorkbook.getSheetAt(0);// 这里只处理第一张表
			// 获得该表中最大行数
			int maxRowNum = sheet.getLastRowNum();// 拿到的是最后一行的行数，是从0开始的
			// 循环拿出里面的数据，排除第一行表头信息，从1开始循环
			for (int i = 1; i <= maxRowNum; i++) {
				Row row = sheet.getRow(i);// 获得行对象
				short maxCellNum = row.getLastCellNum();// 获取行中的最后一个单元格，也就是拿到单元格的最大数，从0开始，所以它的总数是最大+1
				// 循环拿出单元格里的数据,每一行创建一个String[]来接收每一行的数据
				String[] data = new String[maxCellNum];
				for (int j = 0; j <= maxCellNum; j++) {
					Cell cell = row.getCell(j);// 拿到单元格对象,判断其是否为null
					if (cell != null) {
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							data[j] = cell.getStringCellValue();
						}
						if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
							// 日期类型处理
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();
								data[j] = DateFormatUtils.format(date, "yyyy/MM/dd HH:mm:ss");
							} // java.util.Date类型
							else {
								data[j] = String.valueOf((long) cell.getNumericCellValue());
							}
						}
					}
				}
				// 一行结束后就将这行的String[]放到list中
				stringsList.add(data);
			}
			// 关闭流
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringsList;
	}

	public static void download(String fileName, String filePath, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("application/force-download");
		response.setContentType("text/html;charset=UTF-8");
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		request.setCharacterEncoding("UTF-8");
		try {
			String realPath = request.getSession().getServletContext().getRealPath("/");
			File f = new File(realPath + filePath + fileName);

			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition",
					"attachment; filename=" + new String(fileName.getBytes("utf-8"), "iso8859-1"));
			response.setHeader("Content-Length", String.valueOf(f.length()));
			in = new BufferedInputStream(new FileInputStream(f));
			out = new BufferedOutputStream(response.getOutputStream());
			byte[] data = new byte[1024];
			int len = 0;
			while (-1 != (len = in.read(data, 0, data.length))) {
				out.write(data, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}

}
