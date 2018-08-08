package com.dognessnetwork.document.controller.web;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dognessnetwork.document.config.InvalidSignException;
import com.dognessnetwork.document.util.CreatePdf;
import com.dognessnetwork.document.util.SignUtils;
import com.dognessnetwork.document.util.UploadFileUtil;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ImageUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.json.JSONObject;

@Controller
public class HandlerController {
	@RequestMapping("/")
	public	String	index(){
		System.out.print("1232");
		return "redirect:index.html";
	}
	@PostMapping(value = "/IMG", name = "IMG")
	@ResponseBody
	public JSONObject imageBase64(
			@RequestParam(value="img",required=false) String	data,
			HttpServletRequest request) {
		JSONObject	res	=	new	JSONObject();
		JSONObject	header	=	new	JSONObject();
		if(data==null){
			System.out.println("数据为空");
			header.put("status", 2000);
    		header.put("message", "fail");
    		res.put("header", header);
    		res.put("data", "");
			return res;
		}
		
		System.out.println("data:"+data.length());
		try {
			header.put("status", 2000);
    		header.put("message", "fail");
    		String	result	=	QrCodeUtil.decode(ImageUtil.toImage(data));
    		if(result.length()>0){
    			header.put("status", 1000);
        		header.put("message", "success");
        		
    		}
    		res.put("header", header);
    		res.put("data", result.substring(result.length()-14, result.length()));
			return res;
		} catch (Exception e) {
			header.put("status", 2000);
    		header.put("message", "fail");
    		res.put("header", header);
    		res.put("data", "");
			return res;
		}
		
	}
	@PostMapping(value = "/createQRCode", name = "createQRCode")
	@ResponseBody
	public JSONObject createCode( 	@RequestParam("content")	String	content,
									HttpServletRequest mrequest){
		String path = mrequest.getSession().getServletContext().getRealPath("/");
		StringBuffer uri = mrequest.getRequestURL();  
		String tempContextUrl = uri.delete(uri.length() - mrequest.getRequestURI().length(), uri.length()).toString(); 
		Console.log(content);
        
		JSONObject	res	=	new	JSONObject();
		JSONObject	header	=	new	JSONObject();
		String	fileName	=	System.currentTimeMillis()+"";
		String	finalPath	=	UploadFileUtil.getCreateQRCodePath(path,fileName);
		Console.log("最终路径："+finalPath);
		try {
			BufferedImage bufferedImage = QrCodeUtil.generate(content, 400, 400);
			File	file	=	new File(finalPath);
    		ImageIO.write(bufferedImage,"png",file);
    		header.put("code", 1000);
    		header.put("message", "success");
    		res.put("header", header);
    		res.put("data", tempContextUrl+"/img/createQrCode/"
					+fileName+".png");
			return res;
		} catch (Exception e) {
    		header.put("code", 2000);
    		header.put("message", "fail");
    		res.put("header", header);
    		res.put("data", "");
			return res;
		}
	}
}
