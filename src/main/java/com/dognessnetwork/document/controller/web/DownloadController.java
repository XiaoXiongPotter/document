package com.dognessnetwork.document.controller.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dognessnetwork.document.util.FileToPack;
import com.dognessnetwork.document.util.ZipCompress;

import cn.hutool.core.lang.Console;
@Controller
public class DownloadController {
	@RequestMapping(value = "/download", method = RequestMethod.GET)
    public void Download(HttpServletResponse res,@RequestParam("downLoadPath")	String	downLoadPath) {
      //downLoadPath	=	"E:/apache-tomcat-8.5.28/webapps/img/deviceQr/E/A/A/180730";
		//usr/local/apache-tomcat-8.5.28/webapps/img/deviceQr/E/A/A/180731/EAA18073100001.png
		//downLoadPath.s
      String fileName = downLoadPath.substring(downLoadPath.length()-25,downLoadPath.length()-19);
      String sourceFilePath = downLoadPath.substring(0,downLoadPath.length()-19);
      String zipFilePath = downLoadPath.substring(0,downLoadPath.length()-25);
	  FileToPack.fileToZip(sourceFilePath,zipFilePath, fileName);
      res.setHeader("content-type", "application/octet-stream");
      res.setContentType("application/octet-stream");
      res.setHeader("Content-Disposition", "attachment;filename=" + fileName+".rar");
      byte[] buff = new byte[1024];
      BufferedInputStream bis = null;
      OutputStream os = null;
      try {
        os = res.getOutputStream();
        bis = new BufferedInputStream(new FileInputStream(new File(sourceFilePath+".zip")));
        int i = bis.read(buff);
        while (i != -1) {
          os.write(buff, 0, buff.length);
          os.flush();
          i = bis.read(buff);
        }
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        if (bis != null) {
          try {
            bis.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
      System.out.println("success");
    }
	
	/**
	 * http://manager.dognessnetwork.com/document/DognessPlay.apk
	 * 安卓机器狗与面包机
	 * @param res
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/DognessPlay.apk", method = RequestMethod.GET)
    public String DownloadApk(HttpServletResponse res,HttpServletRequest	req) {
		StringBuffer url = req.getRequestURL();  
		String tempContextUrl = url.delete(url.length() - req.getRequestURI().length(), url.length()).toString(); 
		Console.log(tempContextUrl+"/app/DognessPlay.apk");
		return	"redirect:"+tempContextUrl+"/app/DognessPlay.apk";
	}
	
	/**
	 * http://manager.dognessnetwork.com/document/DognessFeeder
	 * 安卓喂食器与投食器
	 * @param res
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/DognessFeeder", method = RequestMethod.GET)
    public String jj(HttpServletResponse res,HttpServletRequest	req) {
		StringBuffer url = req.getRequestURL();  
		String tempContextUrl = url.delete(url.length() - req.getRequestURI().length(), url.length()).toString(); 
		Console.log(tempContextUrl+"/app/DognessFeeder.apk");
		return	"redirect:"+tempContextUrl+"/app/DognessFeeder.apk";
	}
	
	
	/**
	 * http://manager.dognessnetwork.com/document/DognessPlay_ios
	 * 苹果机器狗与面包机
	 * @param res
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/DognessPlay_ios", method = RequestMethod.GET)
    public String DognessPlay_ios(HttpServletResponse res,HttpServletRequest	req) {
		StringBuffer url = req.getRequestURL();  
		String tempContextUrl = url.delete(url.length() - req.getRequestURI().length(), url.length()).toString(); 
		Console.log(tempContextUrl+"/app/DognessPlay.apk");
		return	"redirect:"+tempContextUrl+"/app/DognessPlay.apk";
	}
	
	/**
	 * http://manager.dognessnetwork.com/document/DognessFeeder_ios
	 * 苹果喂食器与投食器
	 * @param res
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/DognessFeeder_ios", method = RequestMethod.GET)
    public String DognessFeeder_ios(HttpServletResponse res,HttpServletRequest	req) {
		StringBuffer url = req.getRequestURL();  
		String tempContextUrl = url.delete(url.length() - req.getRequestURI().length(), url.length()).toString(); 
		Console.log(tempContextUrl+"/app/DognessFeeder.apk");
		return	"redirect:https://itunes.apple.com/cn/app/id1423516347";
	}
	
}
