package com.dognessnetwork.document.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Constants {

    public final static Logger logger = LoggerFactory.getLogger(Constants.class);

	public final static String APP_ACCESSID="2100300056";
	public final static String APP_SECRET="99786fb9b7958fcb0eccb7c1174725c2";

    public static String NECKLACE_CONTEXT_DOMAIN = "";
    public static String NECKLACE_CONTEXT_PATH = "";
    public static String DOCUMENT_CONTEXT_DOMAIN = "";
    public static String AREA;
	public static String HTTP_KEY = "89622EF8B92F78B7A0FB105AAD1WRUDI";	
	public static String SOCKETURL = "wss://www.dognessnetwork.com/point/websocket/point";
	public final static String TOKEN = "x_auth_token";
	static {
		try {
			Properties prop = new Properties();
			InputStream in = Constants.class.getClassLoader().getResourceAsStream("config.properties");
			prop.load(in);
			HTTP_KEY = prop.getProperty("HTTP_KEY");
			NECKLACE_CONTEXT_DOMAIN = prop.getProperty("NECKLACE_CONTEXT_DOMAIN");
			NECKLACE_CONTEXT_PATH = prop.getProperty("NECKLACE_CONTEXT_PATH");
			DOCUMENT_CONTEXT_DOMAIN = prop.getProperty("DOCUMENT_CONTEXT_DOMAIN");
			AREA = prop.getProperty("AREA");
		} catch (IOException e) {
			logger.error("", e);
		}
	}
	
}
