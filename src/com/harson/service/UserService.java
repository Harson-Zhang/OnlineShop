package com.harson.service;

import java.sql.SQLException;

import com.harson.dao.UserDao;
import com.harson.domain.User;

public class UserService {

	public boolean register(User user) throws SQLException {
		UserDao dao = new UserDao();
		int row = dao.register(user); //返回的是影响行数
		return row>0?true:false; 
	}
	
	// 查询待激活的用户
	public boolean active(String activeCode) throws SQLException {
		UserDao dao = new UserDao();
		int row = dao.active(activeCode); //返回的是影响行数
		return row>0?true:false; 
	}

	// 校验用户名是否存在
	public boolean checkUsername(String username) throws SQLException {
		UserDao userDao = new UserDao();
		Long isExist = userDao.checkUsername(username);
		return isExist>0?true:false;
	}

	// 基本登录功能
	public User login(String username, String password) throws SQLException {
		UserDao dao = new UserDao();
		User user = dao.login(username, password);
		
		return user;
	}

	// 验证是否有未处理订单
	public int verifyUnfinishedOrder(String uid) throws SQLException {
		UserDao dao = new UserDao();
		int unfinished = dao.verifyUnfinishedOrder(uid);
		return unfinished;
	}


}
