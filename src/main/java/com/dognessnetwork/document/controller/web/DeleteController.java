package com.dognessnetwork.document.controller.web;

import java.io.File;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dognessnetwork.document.config.InvalidSignException;
import com.dognessnetwork.document.util.SignUtils;

import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONObject;

@Controller
public class DeleteController {
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
    public 	 JSONObject	Download(
	    		HttpServletRequest rquest,
	    		@RequestParam("imgPath")	String	imgPath,
	    		@RequestParam("randCode")	Long	timeMillis
	    		){
		JSONObject	res	=	new	JSONObject();
		JSONObject	header	=	new	JSONObject();
		
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
		parameters.put("randCode", timeMillis);
		parameters.put("imgPath", imgPath);
		try {
			SignUtils.VerifySign(parameters, rquest.getHeader("sign"));
		} catch (InvalidSignException e) {
			// TODO: handle exception
			header.put("code", 7000);
    		header.put("message", "exception");
    		res.put("header", header);
    		res.put("data", "");
			return res;
		}
		String path = rquest.getSession().getServletContext().getRealPath("/").replaceAll("document", "");
		//path="http://localhost/img/1533039492301.png";
		//path.substring(path.indexOf("img"));
		Console.log(path+imgPath.substring(imgPath.indexOf("img")));
		File	oldFile	=	new	File(path+imgPath.substring(imgPath.indexOf("img")));
		if(oldFile.delete()){
			header.put("code", 1000);
    		header.put("message", "success");
    		res.put("header", header);
    		res.put("data", true);
    		
	        return res;
		}else{
			header.put("code", 2000);
    		header.put("message", "fail");
    		res.put("header", header);
    		res.put("data", false);
	        return res;
		}
	}
}
