package com.harson.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harson.service.UserService;
// 验证当前用户下是否有未完成订单，有则返回其数目
public class VerifyUnfinishedOrderServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uid = request.getParameter("uid");
		UserService service = new UserService();
		int unfinished = 0;	//未处理的订单数目
		try {
			unfinished = service.verifyUnfinishedOrder(uid);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		response.getWriter().write(String.valueOf(unfinished));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
