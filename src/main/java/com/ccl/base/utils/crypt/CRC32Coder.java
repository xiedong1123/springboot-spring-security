package com.ccl.base.utils.crypt;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

import org.apache.commons.io.IOUtils;

/**
 * crc32校验方式，此种校验方式与md5、sha1的区别在于多用于通信数据的校验
 * @ClassName CRC32Coder
 * @Description TODO
 * @author 向阳
 * @Date 2017年1月11日 下午1:23:13
 * @version 1.0.0
 */
public class CRC32Coder {
	
	
	/**
     * CRC32字节校验
     * 
     * @param bytes
     *            an array of byte.
     * @return a {@link java.lang.String} object.
     */
    public static String crc32(byte[] bytes) {
        CRC32 crc32 = new CRC32();
        crc32.update(bytes);
        return Long.toHexString(crc32.getValue());
    }

    /**
     * CRC32字符串校验
     * 
     * @param str
     *            a {@link java.lang.String} object.
     * @param charset
     *            a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String crc32(final String str, String charset) {
        try {
            byte[] bytes = str.getBytes(charset);
            return crc32(bytes);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * CRC32字符串校验,默认UTF-8编码读取
     * 
     * @param str
     *            a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String crc32(final String str) {
        return crc32(str, CryptUtils.DEFAULT_CHARSET);
    }

    /**
     * CRC32流校验
     * 
     * @param input
     *            a {@link java.io.InputStream} object.
     * @return a {@link java.lang.String} object.
     */
    public static String crc32(InputStream input) {
        CRC32 crc32 = new CRC32();
        CheckedInputStream checkInputStream = null;
        int test = 0;
        try {
            checkInputStream = new CheckedInputStream(input, crc32);
            do {
                test = checkInputStream.read();
            } while (test != -1);
            return Long.toHexString(crc32.getValue());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * CRC32文件唯一校验
     * 
     * @param file
     *            a {@link java.io.File} object.
     * @return a {@link java.lang.String} object.
     */
    public static String crc32(File file) {
        InputStream input = null;
        try {
            input = new BufferedInputStream(new FileInputStream(file));
            return crc32(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * CRC32文件唯一校验
     * 
     * @param url
     *            a {@link java.net.URL} object.
     * @return a {@link java.lang.String} object.
     */
    public static String crc32(URL url) {
        InputStream input = null;
        try {
            input = url.openStream();
            return crc32(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }
}
