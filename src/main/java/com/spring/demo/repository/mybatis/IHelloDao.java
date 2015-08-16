package com.spring.demo.repository.mybatis;


import com.spring.model.UserInfo;
import com.spring.util.IMyBatisRepository;

//使用注解方式，使得能够自动扫描
@IMyBatisRepository
public interface IHelloDao {
	
	//根据用户名在数据库中进行查找
	public UserInfo selectUserInfoByUserName(String userName);

}
