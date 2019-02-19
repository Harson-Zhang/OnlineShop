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
//获取客户端发送过来的Cookie，若已经存在用户名和密码，则自动登录
//追加“记住用户”功能：从客户端发来的Cookie中获取用户名和密码，传给login.jsp用于自动输入
public class AutoLoginFilter implements Filter{
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;	//先将Servlet转为HttpServlet，就可以使用session域啦
		HttpServletResponse resp = (HttpServletResponse) response;	
		HttpSession session = req.getSession(); //获取当前会话
		Cookie[] cookies = req.getCookies(); //获取客户端发送过来的cookies
		//从cookie中获取username, password, autoLogin和rememberUser
		String username = null;
		String password = null;
		String autoLogin = null;
		String rememberUser = null;
		
		if(cookies != null){
			for(Cookie cookie:cookies){
				if("cookie_username".equals(cookie.getName())){
					username = cookie.getValue();
					username = URLDecoder.decode(username, "UTF-8"); //此处对username进行解码
				}
				if("cookie_password".equals(cookie.getName())){
					password = cookie.getValue();
				}
				if("cookie_autoLogin".equals(cookie.getName())){
					autoLogin = cookie.getValue();
				}
				if("cookie_rememberUser".equals(cookie.getName())){
					rememberUser = cookie.getValue();
				}
			}
		}
		
		//利用username和password，完成登录功能
		if(username!=null && password!=null && autoLogin!=null){
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
		}else if(username!=null && password!=null && rememberUser!=null){
			//将username和password存入request域，实现“记住用户”
			request.setAttribute("usernameForLogin", username);
			request.setAttribute("passwordForLogin", password);
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
