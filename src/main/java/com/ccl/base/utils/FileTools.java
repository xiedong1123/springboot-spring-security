package com.ccl.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.thoughtworks.xstream.core.BaseException;


/**
 * 
 * @ClassName：FileTools
 * @Description：上传文件验证
 * @Author：xiedong
 * @Date：2017年9月4日下午1:25:18
 * @version：1.0.0
 */
public class FileTools {

	public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();

	/*-----------------------------目前可以识别的类型----------------------------*/
	private static void getAllFileType() {
		FILE_TYPE_MAP.put("jpg", "FFD8FF"); //JPEG        
		FILE_TYPE_MAP.put("jpeg", "FFD8FF"); //JPEG        
		FILE_TYPE_MAP.put("png", "89504E47"); //PNG        
		FILE_TYPE_MAP.put("gif", "47494638"); //GIF       
		FILE_TYPE_MAP.put("tif", "49492A00"); //TIFF      
		FILE_TYPE_MAP.put("bmp", "424D"); //Windows Bitmap       
		FILE_TYPE_MAP.put("dwg", "41433130"); //CAD     
		FILE_TYPE_MAP.put("html", "68746D6C3E"); //HTML      
		FILE_TYPE_MAP.put("rtf", "7B5C727466"); //Rich Text Format      
		FILE_TYPE_MAP.put("xml", "3C3F786D6C");
		FILE_TYPE_MAP.put("zip", "504B0304");
		FILE_TYPE_MAP.put("rar", "52617221");
		FILE_TYPE_MAP.put("psd", "38425053"); //PhotoShop    
		FILE_TYPE_MAP.put("eml", "44656C69766572792D646174653A"); //Email [thorough only]     
		FILE_TYPE_MAP.put("dbx", "CFAD12FEC5FD746F"); //Outlook Express     
		FILE_TYPE_MAP.put("pst", "2142444E"); //Outlook        
		FILE_TYPE_MAP.put("office", "D0CF11E0"); //office类型，包括doc、xls和ppt       
		FILE_TYPE_MAP.put("mdb", "000100005374616E64617264204A"); //MS Access       
		FILE_TYPE_MAP.put("wpd", "FF575043"); //WordPerfect     
		FILE_TYPE_MAP.put("eps", "252150532D41646F6265");
		FILE_TYPE_MAP.put("ps", "252150532D41646F6265");
		FILE_TYPE_MAP.put("pdf", "255044462D312E"); //Adobe Acrobat     
		FILE_TYPE_MAP.put("qdf", "AC9EBD8F"); //Quicken    
		FILE_TYPE_MAP.put("pwl", "E3828596"); //Windows Password   
		FILE_TYPE_MAP.put("wav", "57415645"); //Wave     
		FILE_TYPE_MAP.put("avi", "41564920");
		FILE_TYPE_MAP.put("ram", "2E7261FD"); //Real Audio       
		FILE_TYPE_MAP.put("rm", "2E524D46"); //Real Media       
		FILE_TYPE_MAP.put("mpg", "000001BA"); //       
		FILE_TYPE_MAP.put("mov", "6D6F6F76"); //Quicktime       
		FILE_TYPE_MAP.put("asf", "3026B2758E66CF11"); //Windows Media      
		FILE_TYPE_MAP.put("mid", "4D546864"); //MIDI (mid)       
	}

	/** 
	 * 通过读取文件头部获得文件类型 
	 * @param file 
	 * @return 文件类型 
	 * @throws IOException 
	 * @throws BaseException 
	 */
	public static String getFileType(File file) throws IOException {
		getAllFileType();
		String fileExtendName = null;
		FileInputStream is;
		is = new FileInputStream(file);
		byte[] b = new byte[16];
		is.read(b, 0, b.length);
		String filetypeHex = String.valueOf(bytesToHexString(b));
		Iterator<Entry<String, String>> entryiterator = FILE_TYPE_MAP.entrySet().iterator();
		while (entryiterator.hasNext()) {
			Entry<String, String> entry = entryiterator.next();
			String fileTypeHexValue = entry.getValue();
			if (filetypeHex.toUpperCase().startsWith(fileTypeHexValue)) {
				fileExtendName = entry.getKey();
				if (fileExtendName.equals("office")) {
					fileExtendName = getOfficeFileType(is);
				}
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}

		// 如果不是上述类型，则判断扩展名  
		if (fileExtendName == null) {
			String fileName = file.getName();
			// 如果无扩展名，则直接返回空串  
			if (-1 == fileName.indexOf(".")) {
				return "";
			}
			// 如果有扩展名，则返回扩展名  
			return fileName.substring(fileName.indexOf(".") + 1);
		}
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileExtendName;
	}


	/** 
	 * 判断office文件的具体类型 
	 * @param fileInputStream 
	 * @return office文件具体类型 
	 * @throws IOException 
	 * @throws BaseException 
	 */
	private static String getOfficeFileType(FileInputStream fileInputStream) throws IOException {
		String officeFileType = "doc";
		byte[] b = new byte[512];
		fileInputStream.read(b, 0, b.length);
		String filetypeHex = String.valueOf(bytesToHexString(b));
		String flagString = filetypeHex.substring(992, filetypeHex.length());
		if (flagString.toLowerCase().startsWith("eca5c")) {
			officeFileType = "doc";
		} else if (flagString.toLowerCase().startsWith("fdffffff09")) {
			officeFileType = "xls";

		} else if (flagString.toLowerCase().startsWith("09081000000")) {
			officeFileType = "xls";
		} else {
			officeFileType = "ppt";
		}
		return officeFileType;
	}
	
	/** 
	 * 判断office文件的具体类型 
	 * @param fileInputStream 
	 * @return office文件具体类型 
	 * @throws IOException 
	 * @throws BaseException 
	 */
	private static String getOfficeFileType(InputStream fileInputStream) throws IOException {
		String officeFileType = "doc";
		byte[] b = new byte[512];
		fileInputStream.read(b, 0, b.length);
		String filetypeHex = String.valueOf(bytesToHexString(b));
		String flagString = filetypeHex.substring(992, filetypeHex.length());
		if (flagString.toLowerCase().startsWith("eca5c")) {
			officeFileType = "doc";
		} else if (flagString.toLowerCase().startsWith("fdffffff09")) {
			officeFileType = "xls";
			
		} else if (flagString.toLowerCase().startsWith("09081000000")) {
			officeFileType = "xls";
		} else {
			officeFileType = "ppt";
		}
		return officeFileType;
	}

	/** 
	 * 获得文件头部字符串 
	 * @param src 
	 * @return 
	 */
	private static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static boolean checkFileType(MultipartFile file) throws IOException {
		String myFileName = file.getOriginalFilename();
		String suffix = StringUtils.lowerCase(myFileName.substring(myFileName.lastIndexOf(".")+1));
		getAllFileType();
//		String fileExtendName = null;
		byte[] b = new byte[16];
		InputStream is =  file.getInputStream();
		is.read(b, 0, b.length);
		String filetypeHex = String.valueOf(bytesToHexString(b));
//		Iterator<Entry<String, String>> entryiterator = FILE_TYPE_MAP.entrySet().iterator();
		String suffixKey = FILE_TYPE_MAP.get(suffix);
		if (StringUtils.isNotBlank(suffixKey) && filetypeHex.toUpperCase().startsWith(suffixKey)) {
			return true;
		}else {
			return false;
		}
//		while (entryiterator.hasNext()) {
//			Entry<String, String> entry = entryiterator.next();
//			String fileTypeHexValue = entry.getValue();
//			if (filetypeHex.toUpperCase().startsWith(fileTypeHexValue)) {
//				fileExtendName = entry.getKey();
//				if (fileExtendName.equals("office")) {
//					fileExtendName = getOfficeFileType(is);
//				}
//				try {
//					if (is != null)
//						is.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				break;
//			}
//		}
//		if(fileExtendName == null)
//			return false;
//		else {
//			
//			if(suffix.equals(fileExtendName))
//				return true;
//			else 
//				return false;
//		}
	}

	public static void main(String[] args) {

		File file = new File("E:/新闻公告.pdm");
		FileInputStream is;
		try {
			is = new FileInputStream(file);
			byte[] b = new byte[16];
			is.read(b, 0, b.length);
			//	            String filetypeHex = String.valueOf(bytesToHexString(b));    
			String fileName = file.getName();
			System.out.println(fileName.substring(fileName.indexOf(".") + 1));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
