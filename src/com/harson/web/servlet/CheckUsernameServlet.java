package com.harson.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harson.service.UserService;

public class CheckUsernameServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// 获取用户名
		String username = request.getParameter("username");
		// 将用户名传给service层进行校验，得到结果
		UserService service = new UserService();
		boolean isExist = false;
		try {
			isExist = service.checkUsername(username);
		} catch (SQLException e) {
			System.out.println("数据库操作失败！");
			e.printStackTrace();
		}
		// 将结果封入response，以json格式传给Ajax（其实和之前的代码相同）
		response.getWriter().write("{\"isExist\":"+isExist+"}");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
