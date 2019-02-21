package com.harson.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		
		Cookie cookie_username = new Cookie("cookie_username", ""); //此处username为已编码的
		Cookie cookie_password = new Cookie("cookie_password", "");
		// 设置cookie的持久化时间为0，覆盖原先cookie
		cookie_username.setMaxAge(0);
		cookie_password.setMaxAge(0);
		// 发送cookie
		response.addCookie(cookie_username);
		response.addCookie(cookie_password);
		
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
