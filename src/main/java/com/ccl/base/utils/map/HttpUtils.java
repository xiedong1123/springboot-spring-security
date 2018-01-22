package com.ccl.base.utils.map;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用于模拟HTTP请求中GET/POST方式
 * 
 * @author ccl
 *
 */
public class HttpUtils {
	/**
	 * 发送GET请求
	 * 
	 * @param url
	 *            目的地址
	 * @param parameters
	 *            请求参数，Map类型。
	 * @return 远程响应结果
	 */
	public static String sendGet(String url, Map<String, String> parameters) {
		String result = "";
		BufferedReader in = null;// 读取响应输入流
		StringBuffer sb = new StringBuffer();// 存储参数
		String params = "";// 编码之后的参数
		try {
			// 编码请求参数
			if (parameters.size() == 1) {
				for (String name : parameters.keySet()) {
					sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"));
				}
				params = sb.toString();
			} else {
				for (String name : parameters.keySet()) {
					sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"))
							.append("&");
				}
				String temp_params = sb.toString();
				params = temp_params.substring(0, temp_params.length() - 1);
			}
			String full_url = url + "?" + params;
			System.out.println(full_url);
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(full_url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();
			// 设置通用属性
			httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			// 建立实际的连接
			httpConn.connect();
			// 响应头部获取
			Map<String, List<String>> headers = httpConn.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : headers.keySet()) {
				System.out.println(key + "\t：\t" + headers.get(key));
			}
			// 定义BufferedReader输入流来读取URL的响应,并设置编码方式
			in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			String line;
			// 读取返回的内容
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 发送POST请求
	 * 
	 * @param url
	 *            目的地址
	 * @param parameters
	 *            请求参数，Map类型。
	 * @return 远程响应结果
	 */
	public static String sendPost(String url, Map<String, String> parameters) {
		String result = "";// 返回的结果
		BufferedReader in = null;// 读取响应输入流
		PrintWriter out = null;
		StringBuffer sb = new StringBuffer();// 处理请求参数
		String params = "";// 编码之后的参数
		try {
			// 编码请求参数
			if (parameters.size() == 1) {
				for (String name : parameters.keySet()) {
					sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"));
				}
				params = sb.toString();
			} else {
				for (String name : parameters.keySet()) {
					sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"))
							.append("&");
				}
				String temp_params = sb.toString();
				params = temp_params.substring(0, temp_params.length() - 1);
			}
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL.openConnection();
			// 设置通用属性
			httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			// 设置POST方式
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			// 获取HttpURLConnection对象对应的输出流
			out = new PrintWriter(httpConn.getOutputStream());
			// 发送请求参数
			out.write(params);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应，设置编码方式
			in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			String line;
			// 读取返回的内容
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	class HttpPostEmulator {
		// 每个post参数之间的分隔。随意设定，只要不会和其他的字符串重复即可。
		private static final String BOUNDARY = "----------HV2ymHFg03ehbqgZCaKO6jyH";

		public String sendHttpPostRequest(String serverUrl, ArrayList<FormFieldKeyValuePair> generalFormFields,
				ArrayList<UploadFileItem> filesToBeUploaded) throws Exception {
			// 向服务器发送post请求
			URL url = new URL(serverUrl/* "http://127.0.0.1:8080/test/upload" */);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// 发送POST请求必须设置如下两行
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Charset", "UTF-8");
			connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

			// 头
			String boundary = BOUNDARY;
			// 传输内容
			StringBuffer contentBody = new StringBuffer("--" + BOUNDARY);
			// 尾
			String endBoundary = "\r\n--" + boundary + "--\r\n";

			OutputStream out = connection.getOutputStream();

			// 1. 处理普通表单域(即形如key = value对)的POST请求
			for (FormFieldKeyValuePair ffkvp : generalFormFields) {
				contentBody.append("\r\n").append("Content-Disposition: form-data; name=\"")
						.append(ffkvp.getKey() + "\"").append("\r\n").append("\r\n").append(ffkvp.getValue())
						.append("\r\n").append("--").append(boundary);
			}
			String boundaryMessage1 = contentBody.toString();
			out.write(boundaryMessage1.getBytes("utf-8"));

			// 2. 处理文件上传
			for (UploadFileItem ufi : filesToBeUploaded) {
				contentBody = new StringBuffer();
				contentBody.append("\r\n").append("Content-Disposition:form-data; name=\"")
						.append(ufi.getFormFieldName() + "\"; ") // form中field的名称
						.append("filename=\"").append(ufi.getFileName() + "\"") // 上传文件的文件名，包括目录
						.append("\r\n").append("Content-Type:application/octet-stream").append("\r\n\r\n");

				String boundaryMessage2 = contentBody.toString();
				out.write(boundaryMessage2.getBytes("utf-8"));

				// 开始真正向服务器写文件
				File file = new File(ufi.getFileName());
				DataInputStream dis = new DataInputStream(new FileInputStream(file));
				int bytes = 0;
				byte[] bufferOut = new byte[(int) file.length()];
				bytes = dis.read(bufferOut);
				out.write(bufferOut, 0, bytes);
				dis.close();
				contentBody.append("------------HV2ymHFg03ehbqgZCaKO6jyH");

				String boundaryMessage = contentBody.toString();
				out.write(boundaryMessage.getBytes("utf-8"));
				// System.out.println(boundaryMessage);
			}
			out.write("------------HV2ymHFg03ehbqgZCaKO6jyH--\r\n".getBytes("UTF-8"));

			// 3. 写结尾
			out.write(endBoundary.getBytes("utf-8"));
			out.flush();
			out.close();

			// 4. 从服务器获得回答的内容
			String strLine = "";
			String strResponse = "";

			InputStream in = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			while ((strLine = reader.readLine()) != null) {
				strResponse += strLine + "\n";
			}
			// System.out.print(strResponse);

			return strResponse;
		}
	}

	class FormFieldKeyValuePair implements Serializable {

		private static final long serialVersionUID = 1L;
		private String key;
		private String value;

		public FormFieldKeyValuePair(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	// 一个POJO。用于保存上传文件的相关信息
	class UploadFileItem implements Serializable {

		private static final long serialVersionUID = 1L;

		private String formFieldName;

		private String fileName;

		public UploadFileItem(String formFieldName, String fileName) {
			this.formFieldName = formFieldName;
			this.fileName = fileName;
		}

		public String getFormFieldName() {
			return formFieldName;
		}

		public void setFormFieldName(String formFieldName) {
			this.formFieldName = formFieldName;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
	}

	public static void main(String[] args) throws Exception {
		// 设定服务地址
		YunTuUtil yunTuUtil = new YunTuUtil();
		String serverUrl = YunTuUtil.BATCHCREATE_DATA_URL;

		HttpUtils httpUtils = new HttpUtils();
		// 设定要上传的普通Form Field及其对应的value
		// 类FormFieldKeyValuePair的定义见后面的代码
		ArrayList<FormFieldKeyValuePair> ffkvp = new ArrayList<FormFieldKeyValuePair>();
		ffkvp.add(httpUtils.new FormFieldKeyValuePair("key", yunTuUtil.key));
		ffkvp.add(httpUtils.new FormFieldKeyValuePair("tableid", yunTuUtil.tableId));
		ffkvp.add(httpUtils.new FormFieldKeyValuePair("_name", "name"));
		ffkvp.add(httpUtils.new FormFieldKeyValuePair("longitude", "x"));
		ffkvp.add(httpUtils.new FormFieldKeyValuePair("latitude", "y"));

		// 设定要上传的文件。UploadFileItem见后面的代码
		ArrayList<UploadFileItem> ufi = new ArrayList<UploadFileItem>();
		ufi.add(httpUtils.new UploadFileItem("file", "C:\\Users\\ccl\\Desktop\\data.csv"));

		// 类HttpPostEmulator的定义，见后面的代码
		HttpPostEmulator hpe = httpUtils.new HttpPostEmulator();
		String response = hpe.sendHttpPostRequest(serverUrl, ffkvp, ufi);
		
		
		System.out.println("Responsefrom server is: " + response);// id:598bdf027bbf190cbd4d443f
	}

}