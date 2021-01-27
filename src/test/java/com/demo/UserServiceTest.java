package com.demo;

import org.junit.Test;

import com.demo.model.User;
import com.demo.service.UserService;
import com.demo.spring.BeanFactory;
import com.demo.spring.ClassPathXmlApplicationContext;

public class UserServiceTest {

	@Test
	public void testAdd() throws Exception {
		
		BeanFactory applicationContext = new ClassPathXmlApplicationContext();
		UserService service = (UserService) applicationContext.getBean("userService");

		User u = new User();
		u.setUsername("jerome");
		u.setPassword("jeromepwd");
		service.add(u);
	}

}
