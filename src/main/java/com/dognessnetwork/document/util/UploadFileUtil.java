package com.dognessnetwork.document.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.servlet.http.Part;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


public class UploadFileUtil {
	   public static String getMultipartFileType(CommonsMultipartFile file) {
			if (file != null) {
				String imgPath = file.getOriginalFilename();
				int idx = imgPath.lastIndexOf(".");
				if (idx > 0) {
					return imgPath.substring(idx + 1);
				}
			}
			return "";
		}
	   
	   public static String getPartFileType(Part file) {
			if (file != null) {
				String imgPath = file.getSubmittedFileName();
				int idx = imgPath.lastIndexOf(".");
				if (idx > 0) {
					return imgPath.substring(idx + 1);
				}
			}
			return "";
		}
	    
		public static String saveFile(CommonsMultipartFile file, String path, String filename) {
			if (file == null || file.isEmpty())
				return null;
			// String filename = file.getOriginalFilename();
			File filePath = new File(path);
			File	newPath	=new	File(filePath.getParentFile().getPath()+"/img");
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			if (!newPath.exists()) {
				newPath.mkdirs();
			}
			
			String localfileName = newPath.getPath() + "/" + filename;
			// 写入文件
			File dest = new File(localfileName.toString());
			try {
				file.transferTo(dest);
				return localfileName;

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * 保存图片
		 * @param file
		 * @param path
		 * @param filename
		 * @return
		 */
		public static String saveImage(MultipartFile file, String path, String filename) {
			if (file == null || file.isEmpty())
				return null;
			// String filename = file.getOriginalFilename();
			File filePath = new File(path);
			File	newPath	=new	File(filePath.getParentFile().getPath()+"/img");
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			if (!newPath.exists()) {
				newPath.mkdirs();
			}
			
			String localfileName = newPath.getPath() + "/" + filename;
			System.out.println("localfileName:"+localfileName);
			// 写入文件
			File dest = new File(localfileName);
			try {
				file.transferTo(dest);
				return localfileName;

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		//动物识别保存
		public static String saveAnimalImage(MultipartFile file, String path, String filename) {
			if (file == null || file.isEmpty())
				return null;
			// String filename = file.getOriginalFilename();
			File filePath = new File(path);
			File	newPath	=new	File(filePath.getParentFile().getPath()+"/img");
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			if (!newPath.exists()) {
				newPath.mkdirs();
			}
			
			String localfileName = filePath.getPath() + "/" + filename;

			// 写入文件
			File dest = new File(localfileName.toString());
			try {
				file.transferTo(dest);
				return localfileName;

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * 
		 * @param file
		 * @param path
		 * @param type
		 * @param filename
		 * @return
		 */
		public static String saveQrCodeImage(MultipartFile file, String path,String	type, String filename) {
			if (file == null || file.isEmpty())
				return null;
			// String filename = file.getOriginalFilename();
			File filePath = new File(path);
			File	newPath	=new	File(filePath.getParentFile().getPath()+"/img/deviceQr/"+type);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			if (!newPath.exists()) {
				newPath.mkdirs();
			}
			
			String localfileName = newPath.getPath() + "/" + filename;
			// 写入文件
			File dest = new File(localfileName);
			try {
				file.transferTo(dest);
				return localfileName;

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * 获取文件路径
		 * @param path
		 * @param filename
		 * @return
		 */
		public static String getPath(String path, String filename,String	type,
				String	productModel,String	deviceSession,String	batch) {
			// String filename = file.getOriginalFilename();
			  
			File filePath = new File(path);
			File	newPath	=	new	File(filePath.getParentFile().getPath()
							+	"/img/deviceQr/"
							+	type+"/"
							+	productModel+"/"
							+	deviceSession+"/"
							+	batch+"/");
			
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			if (!newPath.exists()) {
				newPath.mkdirs();
			}
			File	pdfPath	=	new	File(filePath.getParentFile().getPath()
					+	"/img/deviceQr/"
					+	type+"/"
					+	productModel+"/"
					+	deviceSession+"/"
					+	batch+"/pdf/");
			if (!pdfPath.exists()) {
				pdfPath.mkdirs();
			}
			String localfileName = newPath.getPath() + "/" + filename+".png";
			return localfileName;
		}
		
		/**
		 * 获取文件路径
		 * @param path
		 * @param filename
		 * @return
		 */
		public static String getCreateQRCodePath(String path, String filename) {
			// String filename = file.getOriginalFilename();
			  
			File filePath = new File(path);
			File	newPath	=	new	File(filePath.getParentFile().getPath()
							+	"/img/createQrCode/");
			
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			if (!newPath.exists()) {
				newPath.mkdirs();
			}
			
			String localfileName = newPath.getPath() + "/" + filename+".png";
			return localfileName;
		}
		public static boolean deleteFile(String realPath, String sysPath) {
			String filePath = realPath + sysPath.substring(10);
			// System.out.println("filePath" + filePath);
			File dest = new File(filePath);
			if (dest.exists()) {
				return dest.delete();
			}
			return false;
		}
		
		public static String generatorFileName() {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			return Long.toString(calendar.getTimeInMillis());
		}
		
		public static String getMultipartFileType(MultipartFile file) {
			if (file != null) {
				String imgPath = file.getOriginalFilename();
				int idx = imgPath.lastIndexOf(".");
				if (idx > 0) {
					return imgPath.substring(idx + 1);
				}
			}
			return "";
		}

		public static String saveFile(Part file, String path, String filename) {
			// TODO Auto-generated method stub
			if (file == null || file.getSize()==0)
				return null;
			// String filename = file.getOriginalFilename();
			File filePath = new File(path);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			String localfileName = path + "/" + filename;
			try {
				 InputStream in = file.getInputStream();
			        OutputStream out = new FileOutputStream(localfileName);
			        byte[] buffer = new byte[1024];
			        int length = -1;
			        while ((length = in.read(buffer)) != -1) {
			            out.write(buffer, 0, length);
			        }
			        in.close();
			        out.close();
			
				return localfileName;

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
}
