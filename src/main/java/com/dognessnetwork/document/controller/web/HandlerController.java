package com.dognessnetwork.document.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HandlerController {
	@RequestMapping("/")
	public	String	index(){
		System.out.print("1232");
		return "redirect:index.html";
	}
}
