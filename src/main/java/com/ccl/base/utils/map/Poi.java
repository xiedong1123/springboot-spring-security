package com.ccl.base.utils.map;
/**
 * 高德云图数据 data
 * @ClassName：Address
 * @Description：
 * @Author：LiangRenJiang
 * @Date：2017年8月10日上午9:57:44
 * @version：1.0.0
 */
public class Poi {
	// 必填
	private String _name; // 数据名称
	// 当loctype=1时，必填
	private String _location; // 坐标 支持点数据  规则：经度,纬度，经纬度支持到小数点后6位  格式示例：104.394729,31.125698
	// 可选
	private String coordtype; // 可选值：1: gps 2: autonavi 3: baidu 您可输入coordtype=1,或者autonavi
	// 当loctype=2时，必填
	private String _address; // 地址
	
	
	public Poi() {
		super();
	}
	public String get_name() {
		return _name;
	}
	public void set_name(String _name) {
		this._name = _name;
	}
	public String get_location() {
		return _location;
	}
	public void set_location(String _location) {
		this._location = _location;
	}
	public String getCoordtype() {
		return coordtype;
	}
	public void setCoordtype(String coordtype) {
		this.coordtype = coordtype;
	}
	public String get_address() {
		return _address;
	}
	public void set_address(String _address) {
		this._address = _address;
	}
	
	
}
