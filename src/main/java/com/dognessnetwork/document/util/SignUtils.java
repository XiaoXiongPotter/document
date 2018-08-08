package com.dognessnetwork.document.util;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import com.dognessnetwork.document.config.Constants;
import com.dognessnetwork.document.config.InvalidSignException;

public class SignUtils {
	
	/**
	 * @author 冯浩员
	 * @description 创建签名参数
	 * @remark
	 * @param parameters
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	public static String creatSign(SortedMap<Object, Object> parameters){
		String key = "89622EF8B92F78B7A0FB105AAD1WRUDI";
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = entry.getKey().toString();
			Object v = entry.getValue();
			if(null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				if (!sb.toString().equals("")){
					sb.append("&");
				}
				if(v.getClass().equals(Boolean.class) || v.getClass().equals(BigDecimal.class) || v.getClass().equals(Integer.class)) {
					sb.append(k + "=" + v);
				} else {
					sb.append(k + "=" + v.toString());
				}
			}
		}
		if (!sb.toString().equals("")){
			sb.append("&");
		}
		sb.append("key=" + key);
		return MD5.md5(sb.toString()).toUpperCase();
	}
	
	/**
	 * @author 冯浩员
	 * @description 验证签名
	 * @remark
	 * @param parameters
	 * @param request void
	 */
	public static void VerifySign(SortedMap<Object, Object> parameters, String sign){
		String tem_sign = SignUtils.creatSign(parameters);
		if (!tem_sign.equals(sign)){
			throw new InvalidSignException("sign error "+tem_sign+ " "+ sign);
		}
	}
	
}
