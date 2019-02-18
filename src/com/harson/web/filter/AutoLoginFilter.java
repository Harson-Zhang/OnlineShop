package com.harson.web.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.harson.domain.User;
import com.harson.service.UserService;

public class AutoLoginFilter implements Filter{
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;	//先将Servlet转为HttpServlet，就可以使用session域啦
		HttpServletResponse resp = (HttpServletResponse) response;	
		HttpSession session = req.getSession(); //获取当前会话
		Cookie[] cookies = req.getCookies(); //获取客户端发送过来的cookies
		//从cookie中获取username和password
		String username = null;
		String password = null;
		
		if(cookies != null){
			for(Cookie cookie:cookies){
				if("cookie_username".equals(cookie.getName())){
					username = cookie.getValue();
					username = URLDecoder.decode(username, "UTF-8"); //此处对username进行解码
				}
				if("cookie_password".equals(cookie.getName())){
					password = cookie.getValue();
				}
			}
		}
		
		//利用username和password，完成登录功能
		if(username!=null && password!=null){
			// 把数据传到service层，请求调回user对象
			UserService userService = new UserService();
			User user = null;
			try {
				user = userService.login(username, password);
			} catch (SQLException e) {
				System.out.println("数据获取失败...");
				e.printStackTrace();
			}
			
			// 将user对象放入session域中，以便短时间内再次访问时不用查询数据库 
			// 注意：只有登录成功的用户才能产生session，所以不用考虑其他例外
			session.setAttribute("user", user);
		}
		chain.doFilter(request, response); //放行request和response
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}
	
}
