package com.dognessnetwork.document.controller.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.dognessnetwork.document.dto.AipImage;
import com.dognessnetwork.document.util.CutFile;
import com.dognessnetwork.document.util.UploadFileUtil;
import com.google.gson.Gson;
/**
 * = MainController TODO Auto-generated class documentation
 *
 */
@Controller
// @CrossOrigin(origins = "*", maxAge = 3600)
public class MainController {
	@RequestMapping(value = "/image", name = "image")
	@ResponseBody
	// @CrossOrigin
	public String image(@RequestParam("file") MultipartFile file, HttpServletRequest mrequest) {
		
		String path = mrequest.getSession().getServletContext().getRealPath("/");
		System.out.println("path:"+path);
		//path = mrequest.getSession().getServletContext().getRealPath(path);
		String url = "";
		String filename = UploadFileUtil.generatorFileName() + "." + UploadFileUtil.getMultipartFileType(file);
		System.out.println("filename:" + filename);
		String urlh = mrequest.getRequestURL().substring(0,
				mrequest.getRequestURL().length() - mrequest.getRequestURI().length());
		try {
			url = UploadFileUtil.saveImage(file, path, filename);
			System.out.println("filename:" + url);
			return new Gson().toJson(urlh + "/img/"+filename);
		} catch (Exception e) {
			return new Gson().toJson("");
		}
		
	}
	@RequestMapping(value = "/image_animal")
	@ResponseBody
	// @CrossOrigin
	public String image_recognition(@RequestParam("file") MultipartFile file, HttpServletRequest mrequest) {
		String path = mrequest.getSession().getServletContext().getRealPath("/");
		System.out.println("path:"+path);
		
		String filename = UploadFileUtil.generatorFileName() + "." + UploadFileUtil.getMultipartFileType(file);
		System.out.println("filename:" + filename);
		
        String urlh = mrequest.getRequestURL().substring(0,
				mrequest.getRequestURL().length() - mrequest.getRequestURI().length());
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
	        JSONObject res = client.animalDetect(path + "/"+filename, options);
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
	        
	        System.out.println(res.toString(0));
	        res.putOpt("path", urlh + "/img/animal/"+filename);
	        return new Gson().toJson(res.toString(0));
		} catch (Exception e) {
			return new Gson().toJson("");
		}
		
	}
}
