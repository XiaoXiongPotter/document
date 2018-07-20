package com.dognessnetwork.document.dto;

import com.baidu.aip.imageclassify.AipImageClassify;

public class AipImage {
	 //设置APPID/AK/SK
    public static final String APP_ID = "11276061";
    public static final String API_KEY = "Z5T2UQljgOmD6iL74GkN87GP";
    public static final String SECRET_KEY = "wVWTslIOBnVFNZ9N0QDQ2ynatngWxN6z";
    
    private AipImage() {}  
    private static AipImageClassify single=null;  
    //静态工厂方法   
    public static AipImageClassify getInstance() {  
         if (single == null) {    
             single = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);
         }    
        return single;  
    }  
    
}
