package com.dognessnetwork.document;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class DocumentApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(DocumentApplication.class, args);
	}
	//如果在tomcat下运行则去掉此注释
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		   return builder.sources(DocumentApplication.class);
	}
}
