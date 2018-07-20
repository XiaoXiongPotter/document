package com.dognessnetwork.document.util;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
 
public class CutFile {
 
	
	public static void main(String[] args) {
		File file1 = new File("C:\\Users\\Administrator\\Desktop\\a.jpg");
		File file2 = new File("D:\\a.jpg");
		//在程序结束时删除文件1
		file1.deleteOnExit();
		try {
			//在D盘创建文件2
			file2.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		cutFile(file1, file2);
	}
	public static void cutFile(File copy, File paste){
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		byte[] bytes = new byte[1024];
		int temp = 0;
		try {
			inputStream = new FileInputStream(copy);
			fileOutputStream = new FileOutputStream(paste);
			while((temp = inputStream.read(bytes)) != -1){
				fileOutputStream.write(bytes, 0, temp);
				fileOutputStream.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (inputStream != null) {
				try {
					
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}