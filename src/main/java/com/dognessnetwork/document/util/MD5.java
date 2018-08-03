package com.dognessnetwork.document.util;


import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5 {
    public static final Logger log = LoggerFactory.getLogger(MD5.class);
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * @author 冯浩员
	 * @description 转换字节数组为16进制字串
	 * @remark
	 * @param b
	 * @return String 16进制字串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuilder resultSb = new StringBuilder();
		for (byte aB : b) {
			resultSb.append(byteToHexString(aB));
		}
		return resultSb.toString();
	}

	/**
	 * @author 冯浩员
	 * @description 转换byte到16进制
	 * @remark
	 * @param b
	 * @return String
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * @author 冯浩员
	 * @description MD5编码
	 * @remark
	 * @param origin
	 * @return String
	 */
	public static String md5(String origin) {
		String resultString = null;
		try {
			resultString = origin;
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes("utf-8")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}
}
