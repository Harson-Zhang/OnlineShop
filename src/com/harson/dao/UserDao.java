package com.harson.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.harson.domain.User;
import com.harson.utils.DataSourceUtils;

public class UserDao {

	public int register(User user) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "Insert INTO user VALUES(?,?,?,?,?,?,?,?,?,?);";
		int row = runner.update(sql, user.getUid(), user.getUsername(), user.getPassword(), user.getName(), user.getEmail(), 
				user.getTelephone(), user.getBirthday(), user.getSex(), user.getState(), user.getCode()); //更新的行数
		return row;
	}

	public int active(String activeCode) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update user set state = ? where code = ?";
		int row = runner.update(sql, 1, activeCode);
		return row;
	}

	public Long checkUsername(String username) throws SQLException {
		// 获取用户名为username的数据的count，并返回
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from user where username = ?";
		Long query = (Long) runner.query(sql, new ScalarHandler(), username);
		return query;
	}

	//从数据库中获取用户信息
	public User login(String username, String password) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username = ? and password = ?";
		User query = runner.query(sql, new BeanHandler<User>(User.class), username, password);
		
		return query;
	}
	
	//返回未处理的订单数
	public int verifyUnfinishedOrder(String uid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from orders where uid = ?";
		Long query = (Long)runner.query(sql, new ScalarHandler(), uid);
		return query.intValue();
	}
}
