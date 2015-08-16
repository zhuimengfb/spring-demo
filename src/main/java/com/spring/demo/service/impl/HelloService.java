package com.spring.demo.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.demo.repository.mybatis.IHelloDao;
import com.spring.demo.service.IHelloService;
import com.spring.model.UserInfo;
@Service
public class HelloService implements IHelloService {

	@Autowired
	IHelloDao helloDao;
	
	@Override
	public Map<String,String> login(Map<String,Object> map){
		Map<String,String> loginMap=new HashMap<String,String>();
		UserInfo userInfo=helloDao.selectUserInfoByUserName((String)map.get("userName"));
		if (userInfo==null){
			loginMap.put("loginCode", "1");
		}
		else {
			if (StringUtils.equals(userInfo.getPassword(), (String)map.get("password"))){
				loginMap.put("loginCode", "0");
			}
			else {
				loginMap.put("loginCode", "2");
			}
		}
		return loginMap;
	}
}
