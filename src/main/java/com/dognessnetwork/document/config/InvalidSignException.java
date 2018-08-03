package com.dognessnetwork.document.config;

/**
 * @author 张金云
 * @date 2017年8月25日
 * @description sign值不正确
 * @remark
 */
public class InvalidSignException extends RuntimeException {

	private static final long serialVersionUID = 3713581129268671453L;

	public InvalidSignException(String ex) {
		super(ex);
	}
}
