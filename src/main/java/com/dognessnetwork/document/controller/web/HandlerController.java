package com.dognessnetwork.document.controller.web;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.dognessnetwork.document.config.InvalidSignException;
import com.dognessnetwork.document.dto.AipImage;
import com.dognessnetwork.document.util.CreatePdf;
import com.dognessnetwork.document.util.CutFile;
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
		
		//System.out.println("data:"+data.length());
		
		try {
			header.put("status", 2000);
    		header.put("message", "fail");
    		String	result	=	QrCodeUtil.decode(ImageUtil.toImage(data));
    		Console.log(result);
    		if(result.length()>0){
    			header.put("status", 1000);
        		header.put("message", "success");
        		
    		}
    		res.put("header", header);
    		
    		
    		if(result.startsWith("http://appserverus.dognesstech.com:8080/APPServer/info/getPetInfo?lan=en&devid=")){
    			res.put("data", result.substring(result.length()-15, result.length()));
    		}
    		else if(result.startsWith("http://manager.dognessnetwork.com/device/qr?id=")){
    			res.put("data", result.substring(result.length()-14, result.length()));
    		}else if(result.length()==15){
    			res.put("data", result);
    		}
    		
			return res;
		} catch (Exception e) {
			header.put("status", 2000);
    		header.put("message", "fail");
    		res.put("header", header);
    		res.put("data", "com.google.zxing.NotFoundException: null");
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
    		header.put("status", 1000);
    		header.put("message", "success");
    		res.put("header", header);
    		res.put("data", tempContextUrl+"/img/createQrCode/"
					+fileName+".png");
			return res;
		} catch (Exception e) {
    		header.put("status", 2000);
    		header.put("message", "fail");
    		res.put("header", header);
    		res.put("data", "");
			return res;
		}
	}


	@RequestMapping(value = "/is_animal")
	@ResponseBody
	public JSONObject image_recognition(
			@RequestParam("imageBase64") String	data,
			@RequestParam("randCode")	Long	timeMillis,
			HttpServletRequest request) {
		JSONObject	res	=	new	JSONObject();
		JSONObject	header	=	new	JSONObject();
		
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
		parameters.put("randCode", timeMillis);
		parameters.put("imageBase64", data);
		try {
			SignUtils.VerifySign(parameters, request.getHeader("sign"));
		} catch (InvalidSignException e) {
			// TODO: handle exception
			header.put("status", 7000);
    		header.put("message", "exception");
    		res.put("header", header);
    		res.put("data", "");
			return res;
		}
		
		//StringBuffer url = request.getRequestURL();  
	    //String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString(); 
	   // Console.log(tempContextUrl);
		//String filename = UploadFileUtil.generatorFileName() + "." + UploadFileUtil.getMultipartFileType(file);
		//System.out.println("filename:" + filename);
		
	   /* String urlh = request.getRequestURL().substring(0,
	    		request.getRequestURL().length() - request.getRequestURI().length());*/
		String	fileName	=	System.currentTimeMillis()+".png";
		String path = request.getSession().getServletContext().getRealPath("/");
		System.out.println("path:"+path);
		//File filePath = new File(path);
		File	newPath	=new	File(path+fileName);
		/*if (!filePath.exists()) {
			filePath.mkdirs();
		}*/
		if (!newPath.exists()) {
			newPath.mkdirs();
		}
		try {
			//先保存到项目根目录
			//UploadFileUtil.saveAnimalImage(file, path, filename);
			ImageIO.write(ImageUtil.toImage(data),"png",newPath);
			AipImageClassify client = AipImage.getInstance();
			// 可选：设置网络连接参数
	        client.setConnectionTimeoutInMillis(2000);
	        client.setSocketTimeoutInMillis(60000);
	        // 传入可选参数调用接口
	        HashMap<String, String> options = new HashMap<String, String>();
	        options.put("top_num", "3");
	        // 参数为本地图片路径
	        org.json.JSONObject baiduRes = client.animalDetect(path + "/"+fileName, options);
	        String	name	=	baiduRes.getJSONArray("result").getJSONObject(0).getString("name");
	        Console.log(name);
	        //删除原来的
	        newPath.delete();
	        
	        System.out.println(baiduRes.toString(0));
	        
	        //baiduRes.putOpt("path", tempContextUrl + "/img/animal/"+filename);
	        
			header.put("status", 1000);
			header.put("message", "success");
			res.put("header", header);
			res.put("data", name);
			
	        return res;
		} catch (Exception e) {
			header.put("status", 2000);
			header.put("message", "fail");
			res.put("header", header);
			res.put("data", e);
			return res;
		}
		
	}

}