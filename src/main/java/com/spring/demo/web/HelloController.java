package com.spring.demo.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.demo.service.IHelloService;
import com.spring.util.Servlets;

@Controller
@RequestMapping(value="")
public class HelloController{
	@Autowired
	IHelloService helloService;
	
	@RequestMapping(value="/hello",method={RequestMethod.GET})
	public Object toLogin(Model model,HttpServletRequest request){
		return "login";
	}
	
	@RequestMapping(value="/hello",method={RequestMethod.POST})
	public Object hello(Model model,HttpServletRequest request){
		Map<String,Object> map=Servlets.getParametersStartingWith(request, "");
		Map<String,String> resultMap=helloService.login(map);
		if (StringUtils.equals("0", resultMap.get("loginCode"))){
			model.addAttribute("hello", map.get("userName"));
			return "hello";
		}
		else	
			return "login";
	}

}
