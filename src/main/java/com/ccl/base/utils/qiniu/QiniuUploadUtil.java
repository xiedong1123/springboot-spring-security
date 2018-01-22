package com.ccl.base.utils.qiniu;

import java.io.IOException;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Recorder;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import net.sf.json.JSONObject;

public class QiniuUploadUtil {
	String key = "key.png";

	String filePath = "/.../...";

	public static String getToken(String key) {
		Auth auth = Auth.create(QiniuConfig.ACCESS_KEY, QiniuConfig.SECRET_KEY);
		return auth.uploadToken(QiniuConfig.BUCKET, key, 3600, new StringMap().put("insertOnly", 1));
	}

	// 普通上传
	public static String upload(byte[] data, String key) {
		UploadManager uploadManager = new UploadManager();
		Response response;
		String responseBody = null;
		try {
			response = uploadManager.put(data, key, QiniuUploadTokenBuilder.buildUploadToken());
			responseBody = response.bodyString();
		} catch (QiniuException e) {
			response = e.response;
			try {
				JSONObject json = JSONObject.fromObject(response.bodyString());
				json.element("code", e.response.statusCode);
				responseBody = json.toString();
			} catch (QiniuException e1) {
				e1.printStackTrace();
			}
		}
		return responseBody;
	}

	// 断点上传
	public static String upload1(byte[] data, String key) throws IOException {
		
		// 记录断点文件保存的位置
		String recordPath = "/.../...";
		// 实例化recorder对象
		Recorder recorder = new FileRecorder(recordPath);
		// 实例化上传对象，传人recorder
		UploadManager uploadManager = new UploadManager(recorder);
		Response response;
		String responseBody = null;
		try {
			response = uploadManager.put(data, key, QiniuUploadTokenBuilder.buildUploadToken());
			responseBody = response.bodyString();
			System.out.println(responseBody);
		} catch (QiniuException e) {
			response = e.response;
			try {
				JSONObject json = JSONObject.fromObject(response.bodyString());
				json.element("code", e.response.statusCode);
				responseBody = json.toString();
			} catch (QiniuException e1) {
				e1.printStackTrace();
			}
		}
		return responseBody;
	}

}
