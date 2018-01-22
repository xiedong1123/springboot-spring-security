package com.ccl.base.utils.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccl.base.utils.security.crsf.CSRFInterceptor;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 二维码工具类
 * @ClassName QRCodeUtils
 * @Description TODO
 * @author 向阳
 * @Date 2017年1月9日 下午5:07:05
 * @version 1.0.0
 */
public class QRCodeUtils {
	
	private static final Logger LOGGER = LoggerFactory
            .getLogger(CSRFInterceptor.class);
	
	//字符
	private static final String CHARSET = "utf-8";
	
	//默认的纠错等级
	private static final ErrorCorrectionLevel DEFAULT_ERROR_CORRECTION = ErrorCorrectionLevel.L;
	
	//默认的边距
	private static final int DEFAULT_MARGIN = 1;
	
	 /**
	  * 生成二维码  
	  * @Description TODO
	  * @param params
	  */
    public static void generateQRImage(QRCodeParams params){  
        if(params == null 
        		|| StringUtils.isEmpty(params.getContent())
        		||StringUtils.isEmpty(params.getStorePath())
        		||StringUtils.isEmpty(params.getName())
        		||params.getIt() == null){  
        	LOGGER.debug("缺少必要的参数{0}",params);
        	return;
        }
        try{
        	File storePath = new File(params.getStorePath());  
            if(!storePath.exists()){  
            	storePath.mkdirs();  
            } 
            File imageFile = new File(params.getStorePath(),params.getName());
            Hashtable<EncodeHintType, Object> hints = params.getHints();
            if(hints == null){
            	hints = new Hashtable<EncodeHintType, Object>();
            	params.setHints(hints);
            }
            if(!hints.containsKey(EncodeHintType.ERROR_CORRECTION)){
            	hints.put(EncodeHintType.ERROR_CORRECTION, DEFAULT_ERROR_CORRECTION);
            }
            
            if(!hints.containsKey(EncodeHintType.CHARACTER_SET)){
            	hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
            }
            
            if(!hints.containsKey(EncodeHintType.MARGIN)){
            	hints.put(EncodeHintType.MARGIN, DEFAULT_MARGIN);
            }
            
            MatrixToImageConfig config = new MatrixToImageConfig(params.getqColor(), params.getBgColor());  
            BitMatrix bitMatrix = new MultiFormatWriter().encode(params.getContent(),BarcodeFormat.QR_CODE, params.getSize(),params.getSize(), hints);
            if(!StringUtils.isEmpty(params.getLogoPath())){
            	writeToFile(bitMatrix,params);
            	return;
            }
            MatrixToImageWriter.writeToPath(bitMatrix, params.getIt().name(),imageFile.toPath(), config);
        }catch(Exception e){
        	e.printStackTrace();
        	LOGGER.debug("生成二维码出错{0}",e);
        }
    }
    
    /**
     * 生成带logo的二维码    
     * @Description TODO
     * @param matrix
     * @param params
     * @throws IOException
     */
    public static void writeToFile(BitMatrix matrix,QRCodeParams params) throws IOException {    
        BufferedImage image = toBufferedImage(matrix,params);    
        Graphics2D gs = image.createGraphics();
		//载入logo    
        Image img = ImageIO.read(new File(params.getLogoPath()));
        //默认的高、宽
        int ratioWidth = image.getWidth()*2/10;  
        int ratioHeight = image.getHeight()*2/10;  
        int logoWidth = img.getWidth(null)>ratioWidth?ratioWidth:img.getWidth(null);  
        int logoHeight = img.getHeight(null)>ratioHeight?ratioHeight:img.getHeight(null); 
        
        int width = img.getWidth(null);
		int height = img.getHeight(null);
		
        if(params.isLogoIsNeedCompress()){
        	if (params.getLogoWidth() != 0 && width > params.getLogoWidth()) {
        		logoWidth = params.getLogoWidth();
			}
			if (params.getLogoHeigth() != 0 && height > params.getLogoHeigth()) {
				logoHeight = params.getLogoHeigth();
			}
        }
        int x = (image.getWidth() - logoWidth) / 2;   
        int y = (image.getHeight() - logoHeight) / 2;  
        
        gs.drawImage(img, x, y, logoWidth, logoHeight, null);   
        gs.setColor(Color.black);  
        gs.setBackground(Color.WHITE);  
        gs.dispose();    
        img.flush();
        File  f = new File(params.getStorePath()+"/"+params.getName());
        if(!ImageIO.write(image, params.getIt().toString(), f)){    
            throw new IOException("Could not write an image of format " + params.getIt().toString() + " to " + f);      
        }    
    }  
    
    public static BufferedImage toBufferedImage(BitMatrix matrix,QRCodeParams params){    
        int width = matrix.getWidth();    
        int height = matrix.getHeight();    
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);    
            
        for(int x=0;x<width;x++){    
            for(int y=0;y<height;y++){    
                image.setRGB(x, y, matrix.get(x, y) ? params.getqColor() : params.getBgColor());    
            }    
        }    
        return image;       
    }   
    
	/**
	 * 解析二维码
	 * @Description TODO
	 * @param file 
	 * @return
	 * @throws Exception
	 */
	public static String decode(File file) throws Exception {
		BufferedImage image;
		image = ImageIO.read(file);
		if (image == null) {
			return null;
		}
		BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Result result;
		Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
		hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
		result = new MultiFormatReader().decode(bitmap, hints);
		String resultStr = result.getText();
		return resultStr;
	}

	/**
	 * 解析二维码
	 * @Description TODO
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String decode(String path) throws Exception {
		return QRCodeUtils.decode(new File(path));
	}
	
	
	

	public static void main(String[] args) throws Exception {
		String text = "http://www.baidu.com";
		
		//不含Logo
		//QRCodeUtil.encode(text, null, "e:\\", true);
		//含Logo，不指定二维码图片名
		//QRCodeUtil.encode(text, "e:\\csdn.jpg", "e:\\", true);
		//含Logo，指定二维码图片名
		//QRCodeUtils.generateQRImage(text, "e:\\csdn.jpg", "e:\\", "qrcode", true);
	}
}
