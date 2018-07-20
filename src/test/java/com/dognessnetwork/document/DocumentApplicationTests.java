package com.dognessnetwork.document;

import java.io.File;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DocumentApplicationTests {
	@Autowired
	HttpServletRequest mrequest;
	@Test
	public void contextLoads() {
		/*String path = mrequest.getSession().getServletContext().getRealPath("/");
		String	name	=	System.getProperty("os.name");
		System.out.print(name);
		System.out.println("++++++++++++");
		File	file	=	new	File(path+"/123.txt");
		file.getParentFile();
		System.out.println(file.getParentFile().getParent());
		File	file1	=	new	File(file.getParentFile().getParent()+"/img/123.txt");
		System.out.println(file1.getParentFile().getPath());*/
		/*******************************/
		HashMap<String, Object> paramMap = new HashMap<>();
	

		//文件上传只需将参数中的键指定（默认file），值设为文件对象即可，对于使用者来说，文件上传与普通表单提交并无区别
		paramMap.put("file", FileUtil.file("E:/Pictures/tupian/dgmyq.jpg"));
		String result= HttpUtil.post("https://www.dognessnetwork.com/document/image_animal", paramMap);
		Console.log(result);
	}
	
}
