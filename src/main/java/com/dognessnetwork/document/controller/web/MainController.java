package com.dognessnetwork.document.controller.web;

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
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;

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
/**
 * = MainController TODO Auto-generated class documentation
 *
 */
@Controller
public class MainController {
	
	@PostMapping(name = "upload", value="/upload")
	@ResponseBody
    public String  upload_image(@RequestParam("file") MultipartFile file, HttpServletRequest request){
		StringBuffer url = request.getRequestURL();  
        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString(); 
        Console.log(tempContextUrl);
        
		String filename = UploadFileUtil.generatorFileName() + "." + UploadFileUtil.getMultipartFileType(file);
		System.out.println("filename:" + filename);
		JSONObject	res	=	new	JSONObject();
		String path = request.getSession().getServletContext().getRealPath("/");
		System.out.println("path:"+path);
		UploadFileUtil.saveImage(file, path, filename);
		String returnUrl = tempContextUrl + "/img/"+filename;
		res.put("link", returnUrl);
		System.out.println("obj:"+res.toString());
		return res.toString();
    }
	
	@RequestMapping(value = "/image", name = "image")
	@ResponseBody
	public JSONObject image(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		
		
		StringBuffer url = request.getRequestURL();  
        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString(); 
        Console.log(tempContextUrl);
        
		String filename = UploadFileUtil.generatorFileName() + "." + UploadFileUtil.getMultipartFileType(file);
		System.out.println("filename:" + filename);
		/*String urlh = request.getRequestURL().substring(0,
				request.getRequestURL().length() - request.getRequestURI().length());*/
		JSONObject	res	=	new	JSONObject();
		JSONObject	header	=	new	JSONObject();
		String path = request.getSession().getServletContext().getRealPath("/");
		System.out.println("path:"+path);
		try {
			UploadFileUtil.saveImage(file, path, filename);
			System.out.println("filename:" + url);
			header.put("status", 1000);
    		header.put("message", "success");
    		res.put("header", header);
    		res.put("data", tempContextUrl + "/img/"+filename);
			return res;
		} catch (Exception e) {
			header.put("status", 2000);
    		header.put("message", "fail");
    		res.put("header", header);
    		res.put("data", "");
			return res;
		}
		
	}
	@RequestMapping(value = "/image_animal")
	@ResponseBody
	public JSONObject image_recognition(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		StringBuffer url = request.getRequestURL();  
        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString(); 
        Console.log(tempContextUrl);
		String filename = UploadFileUtil.generatorFileName() + "." + UploadFileUtil.getMultipartFileType(file);
		System.out.println("filename:" + filename);
		JSONObject	res	=	new	JSONObject();
		JSONObject	header	=	new	JSONObject();
       /* String urlh = request.getRequestURL().substring(0,
        		request.getRequestURL().length() - request.getRequestURI().length());*/
		String path = request.getSession().getServletContext().getRealPath("/");
		System.out.println("path:"+path);
		try {
			//先保存到项目根目录
			UploadFileUtil.saveAnimalImage(file, path, filename);
			AipImageClassify client = AipImage.getInstance();
			// 可选：设置网络连接参数
	        client.setConnectionTimeoutInMillis(2000);
	        client.setSocketTimeoutInMillis(60000);
	        // 传入可选参数调用接口
	        HashMap<String, String> options = new HashMap<String, String>();
	        options.put("top_num", "3");
	        // 参数为本地图片路径
	        org.json.JSONObject baiduRes = client.animalDetect(path + "/"+filename, options);
	        String	name	=	baiduRes.getJSONArray("result").getJSONObject(0).getString("name");
	        Console.log(name);
	        if(name.equals("非动物")){
	        	
	        }
	        //将图片剪切到img/animal/目录下
	        File	copy	=	new	File(path + "/"+filename);
	        File	mu	=	new	File(copy.getParentFile().getParent()+"/img/animal/");
	        if (!mu.exists()) {
	        	mu.mkdirs();
			}
	      
	        File	paste	=	new	File(mu+"/"+filename);
	        
			try {
				//创建文件
				paste.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//剪切
	        CutFile.cutFile(copy, paste);
	        //删除原来的
	        copy.delete();
	        
	        System.out.println(baiduRes.toString(0));
	        
	        baiduRes.putOpt("path", tempContextUrl + "/img/animal/"+filename);
	        
			header.put("status", 1000);
    		header.put("message", "success");
    		res.put("header", header);
    		res.put("data", baiduRes.toString(0));
    		
	        return res;
		} catch (Exception e) {
			header.put("status", 2000);
    		header.put("message", "fail");
    		res.put("header", header);
    		res.put("data", "");
			return res;
		}
		
	}
	
	@RequestMapping(value = "/qrCode_image")
	@ResponseBody
	public JSONObject qrCode_image(
			HttpServletRequest mrequest,
			@RequestParam("fileName")	String	fileName,
			@RequestParam("type")	String	type,
			@RequestParam("productModel")	String	productModel,
			@RequestParam("deviceSession")	String	deviceSession,
			@RequestParam("content")	String	content,
			@RequestParam(value="size",required=false)	Integer	size) {
		String path = mrequest.getSession().getServletContext().getRealPath("/");
		
		/*String urlh = mrequest.getRequestURL().substring(0,
				mrequest.getRequestURL().length() - mrequest.getRequestURI().length());*/
		StringBuffer uri = mrequest.getRequestURL();  
		String tempContextUrl = uri.delete(uri.length() - mrequest.getRequestURI().length(), uri.length()).toString(); 
		Console.log(tempContextUrl);
		//获取当前时间对象  
        Date date = new Date(); 
        //获取日期格式器
        DateFormat dateFormat = DateFormat.getDateInstance();
        dateFormat = new SimpleDateFormat("yyMMdd");
        String	batch	=	dateFormat.format(date);
        
		Console.log(size);
		if(size==null)size=400;
		JSONObject	res	=	new	JSONObject();
		JSONObject	header	=	new	JSONObject();
		String	finalPath	=	UploadFileUtil.getPath(path,fileName,type,productModel,deviceSession,batch);
		Console.log("最终路径："+finalPath);
		try {
			BufferedImage bufferedImage = QrCodeUtil.generate(content, size, size);
			File	file	=	new File(finalPath);
    		ImageIO.write(bufferedImage,"png",file);
    		header.put("status", 1000);
    		header.put("message", "success");
    		res.put("header", header);
    		Console.log("最终保存路径："+file.getPath());
    		Console.log("最终访问路径："+file.getAbsolutePath());
    		JSONObject	url	=	new	JSONObject();
    		CreatePdf.addImageAbsolu(file.getPath(), file.getParent()+"/pdf/"+fileName+".pdf",content);
    		url.put("url", tempContextUrl+"/img/deviceQr/"
					+	type+"/"
					+	productModel+"/"
					+	deviceSession+"/"
					+	batch+"/"+fileName+".png");
    		url.put("downLoadPath", file.getPath());
    		
    		res.put("data", url);
			return res;
		} catch (Exception e) {
    		header.put("status", 2000);
    		header.put("message", "fail");
    		res.put("header", header);
    		res.put("data", "");
			return res;
		}

	}
	@RequestMapping(value = "/imageBase64", name = "imageBase64")
	@ResponseBody
	public JSONObject imageBase64(
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
		
		
		String path = request.getSession().getServletContext().getRealPath("/");
		System.out.println("path:"+path);
		String	fileName	=	System.currentTimeMillis()+".png";
		/*String urlh = request.getRequestURL().substring(0,
				request.getRequestURL().length() - request.getRequestURI().length());*/
		StringBuffer url = request.getRequestURL();  
		String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString(); 
		
		File filePath = new File(path);
		File	newPath	=new	File(filePath.getParentFile().getPath()+"/img/"+fileName);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		if (!newPath.exists()) {
			newPath.mkdirs();
		}
		
		try {
			ImageIO.write(ImageUtil.toImage(data),"png",newPath);
			System.out.println("filename:" + fileName);
			header.put("status", 1000);
    		header.put("message", "success");
    		res.put("header", header);
    		res.put("data", /*tempContextUrl + */"/img/"+fileName);
			return res;
		} catch (Exception e) {
			header.put("status", 2000);
    		header.put("message", "fail");
    		res.put("header", header);
    		res.put("data", "");
			return res;
		}
		
	}
	
	@RequestMapping(value = "/bufferedImage", name = "bufferedImage")
	@ResponseBody
	public JSONObject bufferedImage(BufferedImage bufferedImage,HttpServletRequest request){
		JSONObject	res	=	new	JSONObject();
		JSONObject	header	=	new	JSONObject();
		String path = request.getSession().getServletContext().getRealPath("/");
		System.out.println("path:"+path);
		String	fileName	=	System.currentTimeMillis()+".png";
		/*String urlh = request.getRequestURL().substring(0,
				request.getRequestURL().length() - request.getRequestURI().length());*/
		StringBuffer url = request.getRequestURL();  
		String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString(); 
		
		File filePath = new File(path);
		File	newPath	=new	File(filePath.getParentFile().getPath()+"/img/"+fileName);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		if (!newPath.exists()) {
			newPath.mkdirs();
		}
		
		try {
			ImageIO.write(bufferedImage,"png",newPath);
			System.out.println("filename:" + fileName);
			header.put("status", 1000);
    		header.put("message", "success");
    		res.put("header", header);
    		res.put("data", tempContextUrl+"/img/"+fileName);
			return res;
		} catch (Exception e) {
			header.put("status", 2000);
    		header.put("message", "fail");
    		res.put("header", header);
    		res.put("data", "");
			return res;
		}
	}
}
