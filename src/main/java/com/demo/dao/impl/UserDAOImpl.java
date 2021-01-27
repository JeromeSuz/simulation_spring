package com.demo.dao.impl;

import com.demo.dao.UserDAO;
import com.demo.model.User;

public class UserDAOImpl implements UserDAO {

	public void save(User user) {
		System.out.println("UserDAOImpl saved()");
		System.out.println("user.toString() = " + user.toString());
	}

}
