package com.harson.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harson.service.UserService;
// 将用户邮箱端发送的激活码接收，从数据库中找出对应用户激活
public class ActiveServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String activeCode = request.getParameter("activeCode");
		//找出对应激活码的用户
		UserService service = new UserService();
		boolean isActiveSucceed = false;
		try {
			isActiveSucceed = service.active(activeCode);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		//如果激活的用户存在，syso找到；否则没找到
		if(isActiveSucceed){
			System.out.println("用户激活成功！");
		}else{
			System.out.println("用户激活失败！");
		}
		//重定向到网站首页
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
