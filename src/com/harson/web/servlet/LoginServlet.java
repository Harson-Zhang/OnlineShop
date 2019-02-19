package com.harson.web.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.harson.domain.User;
import com.harson.service.UserService;
//实现基本登录功能
public class LoginServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); //设置输入的编码格式
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String autoLogin = request.getParameter("autoLogin");
		String rememberUser = request.getParameter("rememberUser");
		
		HttpSession session = request.getSession();
		
		// 把数据传到service层，请求调回user对象
		UserService userService = new UserService();
		User user = null;
		try {
			user = userService.login(username, password);
		} catch (SQLException e) {
			System.out.println("数据获取失败...");
			e.printStackTrace();
		}
		
		// 如果登录成功。。。如果登录失败。。。
		if(user == null){
			System.out.println("不存在该用户！");
		}
		
		if(user != null){
			// 防止中文用户名，先对用户名进行编码
			username = URLEncoder.encode(user.getUsername(), "UTF-8");
			
			// 登录成功后，判断用户是否勾选了“自动登录”和“记住用户”
/*			String autoLogin = request.getParameter("autoLogin");
			String rememberUser = request.getParameter("rememberUser");*/
			if(autoLogin!=null || rememberUser!=null){
				// 如果勾选了“自动登录”或“记住用户”，那么把用户名和密码保存成cookie
				Cookie cookie_username = new Cookie("cookie_username", username); //此处username为已编码的
				Cookie cookie_password = new Cookie("cookie_password", password);
				// 设置cookie的持久化时间为1小时（测试方便，先改成2min）
				cookie_username.setMaxAge(60*60);
				cookie_password.setMaxAge(60*60);
				// 设置cookie的携带路径 --- onlineShop下的任何资源
				cookie_username.setPath(request.getContextPath());
				cookie_password.setPath(request.getContextPath());
				// 发送cookie
				response.addCookie(cookie_username);
				response.addCookie(cookie_password);
				
				//如果勾选了“自动登录”，将autoLogin存为Cookie
				if(autoLogin!=null){
					Cookie cookie_autoLogin = new Cookie("autoLogin", autoLogin);
					cookie_autoLogin.setMaxAge(60*60);	//（测试方便，先改成2min）
					cookie_autoLogin.setPath(request.getContextPath());
					response.addCookie(cookie_autoLogin);
				}
				//“记住用户”同理
				if(rememberUser!=null){
					Cookie cookie_rememberUser = new Cookie("rememberUser", rememberUser);
					cookie_rememberUser.setMaxAge(60*60);	//（测试方便，先改成2min）
					cookie_rememberUser.setPath(request.getContextPath());
					response.addCookie(cookie_rememberUser);
				}
				
			}
			/*else if(rememberUsername != null){
				//若没有勾选“自动登录”，接着是否勾选“记住用户名”
				//其他步骤同上
				Cookie cookie_username = new Cookie("cookie_username", username);
				cookie_username.setMaxAge(120);	//（测试方便，先改成2min）
				cookie_username.setPath(request.getContextPath());
				response.addCookie(cookie_username);
			}*/
			
			// 将user对象放入session域中，以便短时间内再次访问时不用查询数据库 
			session.setAttribute("user", user);
			// 重定向到首页
			response.sendRedirect(request.getContextPath());
			
		}else{
			//将登录失败的信息返回给登录页面
			request.setAttribute("loginInfo", "用户名或密码错误");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
