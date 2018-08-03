package com.dognessnetwork.document.controller.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dognessnetwork.document.util.FileToPack;
import com.dognessnetwork.document.util.ZipCompress;
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
}
