package com.harson.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//实现功能：如果已有用户登录，访问登录页面时就重定向到首页
public class NoNeedToLoginFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		
		// 如果已经登录了，就重定向到首页
		if(session.getAttribute("user")!=null){
			resp.sendRedirect(req.getContextPath());
		}
		chain.doFilter(request, response);
	}

	public void destroy() {
	}


	public void init(FilterConfig fConfig) throws ServletException {
	}

}
