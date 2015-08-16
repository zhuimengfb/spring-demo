package com.spring.test.controller;

import java.util.Map;

import javax.servlet.annotation.ServletSecurity;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="")
public class HelloController {
	
	@RequestMapping(value="hello",method={RequestMethod.POST})
	public Object hello(Model model,HttpServletRequest request){
		Map<String,String[]> map=request.getParameterMap();
		model.addAttribute("hello", "hello:"+map.get("userName")[0]);
		return "hello";
	}

}
