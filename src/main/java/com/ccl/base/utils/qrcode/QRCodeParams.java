package com.ccl.base.utils.qrcode;

import java.util.Hashtable;

import com.google.zxing.EncodeHintType;
/**
 * qr参数类
 * @ClassName QRCodeParams
 * @Description TODO
 * @author 向阳
 * @Date 2017年1月9日 下午5:40:46
 * @version 1.0.0
 */
public class QRCodeParams {
	
	//二维码内容 
	 private String content;                
	
	//qr的大小
	private int size = 300;
	
	//logo的宽度
	private int logoWidth = 60;
	
	//logo的高度
	private int logoHeigth = 60;
	
	//参数
	private Hashtable<EncodeHintType, Object> hints;
	
	//logo是否需要压缩
	private boolean logoIsNeedCompress = false;
	
	//logo路径
	private String logoPath;
	
	//生成图片的类型
	private ImageType it = ImageType.png;
	
	//存储路径，由路径生成器生成
	private String storePath;
	
	//文件名，这个可能由统一的文件名生成算法生成，包含后缀名
	private String name;
	
	//网络路径，由统一的网络路径生成器生成
	private String url;
	
	//背景色
	private int bgColor = 0xFFFFFFFF;
	
	//前景色
	private int qColor =  0xFF000000;
	
	//白边大小，取值范围0~4 
	private Integer margin = 1;
	
	//是否成功
	private boolean isOk;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getLogoWidth() {
		return logoWidth;
	}

	public void setLogoWidth(int logoWidth) {
		this.logoWidth = logoWidth;
	}

	public int getLogoHeigth() {
		return logoHeigth;
	}

	public void setLogoHeigth(int logoHeigth) {
		this.logoHeigth = logoHeigth;
	}

	public Hashtable<EncodeHintType, Object> getHints() {
		return hints;
	}

	public void setHints(Hashtable<EncodeHintType, Object> hints) {
		this.hints = hints;
	}

	public boolean isLogoIsNeedCompress() {
		return logoIsNeedCompress;
	}

	public void setLogoIsNeedCompress(boolean logoIsNeedCompress) {
		this.logoIsNeedCompress = logoIsNeedCompress;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public ImageType getIt() {
		return it;
	}

	public void setIt(ImageType it) {
		this.it = it;
	}

	public String getStorePath() {
		return storePath;
	}

	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getBgColor() {
		return bgColor;
	}

	public void setBgColor(int bgColor) {
		this.bgColor = bgColor;
	}

	public int getqColor() {
		return qColor;
	}

	public void setqColor(int qColor) {
		this.qColor = qColor;
	}

	public Integer getMargin() {
		return margin;
	}

	public void setMargin(Integer margin) {
		this.margin = margin;
	}

	@Override
	public String toString() {
		return "QRCodeParams [content=" + content + ", size=" + size + ", logoWidth=" + logoWidth + ", logoHeigth="
				+ logoHeigth + ", hints=" + hints + ", logoIsNeedCompress=" + logoIsNeedCompress + ", logoPath="
				+ logoPath + ", it=" + it + ", storePath=" + storePath + ", name=" + name + ", url=" + url
				+ ", bgColor=" + bgColor + ", qColor=" + qColor + ", margin=" + margin + "]";
	}

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}            
	
}
