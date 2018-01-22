package com.ccl.base.utils.map;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.ccl.base.utils.crypt.MD5Coder;

import net.sf.json.JSONObject;

public class YunTuUtil {
	
	public String key = "26c12ece0c6fbf1efef55dcba1a4ae9a"; //申请"Web服务API"类型Key
	public String privateKey = "82a999da6071723e0d46a51700977688"; // 私钥
	public String tableId = "598b1ba42376c11dab0c784c";  
	
	public static String CREATE_TABLE_URL = "http://yuntuapi.amap.com/datamanage/table/create"; // 创建数据表 
	public static String CREATE_DATA_URL = "http://yuntuapi.amap.com/datamanage/data/create"; // 创建数据（单条） 
	public static String BATCHCREATE_DATA_URL = "http://yuntuapi.amap.com/datamanage/data/batchcreate"; // 创建数据（批量）
	public static String UPDATE_DATA_URL = "http://yuntuapi.amap.com/datamanage/data/update"; // 更新数据（单条） 
	public static String DELETE_DATA_URL = "http://yuntuapi.amap.com/datamanage/data/delete";// 删除数据（单条/批量） 删除指定tableid的数据表中的数据，一次请求限制删除1-50条数据。
	public static String IMPORTSTATUS_BATCH_URL = "http://yuntuapi.amap.com/datamanage/batch/importstatus";// 进度查询请求地址
	/**
	 * 
	 * @param key 
	 * @param privateKey
	 */
	public YunTuUtil(String key, String privateKey, String tableId) {
		super();
		this.key = key;
		this.privateKey = privateKey;
		this.tableId = tableId;
	}
	
	public YunTuUtil() {
		super();
	}
	
	/**
	 * 获取参数的签名
	 * @MethodName：getSign
	 * @param map
	 * @return
	 * @throws Exception
	 * @ReturnType：String
	 * @Description：
	 * @Creator：LiangRenJiang
	 * @CreateTime：2017年8月10日上午9:02:21
	 * @Modifier：
	 * @ModifyTime：
	 */
	public String getSign(Map<String, String> map) throws Exception {
		// 生成签名的规则  sig=MD5(a=23&b=12&c=67&d=48&f=8bbbbb) 
		TreeMap<String, String> treeMap = new TreeMap<>(map);
		StringBuffer sb = new StringBuffer();
		
		for (Map.Entry<String, String> entry : treeMap.entrySet()) {
			sb.append(entry.getKey() + "=" + entry.getValue() + "&");
		}
		
		String string = sb.toString().substring(0, sb.length() -1) + this.privateKey;
		String sign = MD5Coder.encryptMD5(string);
		return sign;
	}
	/**
	 * 创建一张云图表
	 * @MethodName：createTable
	 * @param name
	 * @return 返回 创建表的tableID
	 * @throws Exception 
	 * @ReturnType：String
	 * @Description：创建一张云图表
	 * @Creator：LiangRenJiang
	 * @CreateTime：2017年8月10日上午10:50:35
	 * @Modifier：
	 * @ModifyTime：
	 */
	public String createTable(String name) throws Exception {
		String tableId = StringUtils.EMPTY;
		Map<String, String> parameters = new HashMap<>();
		parameters.put("key", this.key);
		parameters.put("name", name);
		String sig = this.getSign(parameters);
		parameters.put("sig", sig);
		String json = HttpUtils.sendPost(YunTuUtil.CREATE_TABLE_URL, parameters);
		JSONObject jsonObject = JSONObject.fromObject(json);
		
		String status = jsonObject.get("status").toString();
		String info = jsonObject.getString("info").toString();
//		返回状态  status
//		取值规则：1：成功；0：失败，未知原因；-11：失败，已存在相同名称表 -21：失败，已创建表达到最大数据
//		status = 1，info返回“ok”
		if (StringUtils.equalsIgnoreCase("1", status) && StringUtils.equalsIgnoreCase("ok", info)) {
			tableId = jsonObject.getString("tableid").toString();
			return tableId;
		}
		return tableId;
	}
	
	/**
	 * 创建数据（单条）
	 * @MethodName：create
	 * @param poi
	 * @return 成功创建的数据id
	 * @throws Exception 
	 * @ReturnType：String
	 * @Description： 创建数据（单条）
	 * @Creator：LiangRenJiang
	 * @CreateTime：2017年8月10日上午10:06:09
	 * @Modifier：
	 * @ModifyTime：
	 */
	public String createData(Poi poi) throws Exception {
		String id = StringUtils.EMPTY; // 返回创建数据的行
		
		// 把poi 对象转换成 json
		JSONObject jsonObject = JSONObject.fromObject(poi);
		String data = jsonObject.toString();
		
		// 参数
		Map<String, String> parameters = new HashMap<>();
		parameters.put("key", this.key);
		parameters.put("tableid", this.tableId);
		parameters.put("data", data);
		// 数字签名
		String sig = this.getSign(parameters);
		parameters.put("sig", sig);
		// 发送请求 post
		String json = HttpUtils.sendPost(YunTuUtil.CREATE_DATA_URL, parameters);
		
		jsonObject = JSONObject.fromObject(json);
		String status = jsonObject.get("status").toString();
		String info = jsonObject.getString("info").toString();
		//返回状态 status 取值规则：1：成功；0：失败
		//status = 1，info返回“ok”
		if (StringUtils.equalsIgnoreCase("1", status) && StringUtils.equalsIgnoreCase("ok", info)) {
			id = jsonObject.getString("_id").toString();
			return id;
		}
		return id;
	}
	
	/**
	 * 删除一条云图数据
	 * @MethodName：deleteData
	 * @param dataId
	 * @return
	 * @throws Exception
	 * @ReturnType：String
	 * @Description：
	 * @Creator：chenchuanliang
	 * @CreateTime：2017年8月10日上午11:11:53
	 * @Modifier：
	 * @ModifyTime：
	 */
	public boolean deleteData(Integer... dataId) throws Exception {
		// 拼接URL
		Map<String, String> parameters = new HashMap<>();
		parameters.put("key", this.key);
		parameters.put("tableid", this.tableId);
		String ids = StringUtils.join(dataId, ",");
		parameters.put("ids", ids);
		
		String sig = this.getSign(parameters);
		parameters.put("sig", sig);
		String json = HttpUtils.sendPost(YunTuUtil.DELETE_DATA_URL, parameters);
		JSONObject jsonObject = JSONObject.fromObject(json);
		String status = jsonObject.get("status").toString();
		String info = jsonObject.getString("info").toString();
		//返回状态 status 取值规则：1：成功；0：失败
		//status = 1，info返回“ok”
		if (StringUtils.equalsIgnoreCase("1", status) && StringUtils.equalsIgnoreCase("ok", info)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 批量创建进度查询 
	 * @MethodName：getImportStatusByBatch
	 * @param batchid 批量处理任务唯一标识
	 * @return 处理进度 取值范围[0,100]
	 * @throws Exception 
	 * @ReturnType：int 
	 * @Description：批量创建进度查询 
	 * @Creator：LiangRenJiang
	 * @CreateTime：2017年8月10日下午1:56:16
	 * @Modifier：
	 * @ModifyTime：
	 */
	public int getImportStatusByBatch(String batchid) throws Exception {
		int progress = Integer.valueOf(0);
		Map<String, String> parameters = new HashMap<>();
		parameters.put("key", this.key);
		parameters.put("tableid", this.tableId);
		parameters.put("batchid", batchid);
		
		String sig = this.getSign(parameters);
		parameters.put("sig", sig);
		
		String json = HttpUtils.sendPost(YunTuUtil.IMPORTSTATUS_BATCH_URL, parameters);
		
		JSONObject jsonObject = JSONObject.fromObject(json);
		String status = jsonObject.getString("status");
		String info = jsonObject.getString("info");
		if (StringUtils.equalsIgnoreCase("1", status) && StringUtils.equalsIgnoreCase("ok", info)) {
			progress = Integer.valueOf(jsonObject.getString("progress"));
		}
		return progress;
	}
	
	public static void main(String[] args) throws Exception {
		YunTuUtil yunTuUtil = new YunTuUtil();
		// 测试 数据签名
//		HashMap<String, String> hashMap = new HashMap<>();
//		hashMap.put("a", "23");
//		hashMap.put("f", "23");
//		hashMap.put("c", "23"); 
//		hashMap.put("d", "23");
//		hashMap.put("b", "23");
//		String sign = yunTuUtil.getSign(hashMap);
		
		// 测试删除
//		boolean deleteData = yunTuUtil.deleteData(1,2);
		
		// 测试插入数据
//		Poi poi = new Poi();
//		poi.set_name("aaa");
//		poi.set_location("104.005353,30.65745");
//		poi.set_address("cccc");
//		String create = yunTuUtil.createData(poi);
		
		// 测试创建 数据表
//		String tableId = yunTuUtil.createTable("ccccaaqqqq");
//		System.out.println("tableId + ===========" + tableId);
		
		// 测试批量导入进度
		yunTuUtil.getImportStatusByBatch("1111");
		
//		System.out.println(create);
	}
}
