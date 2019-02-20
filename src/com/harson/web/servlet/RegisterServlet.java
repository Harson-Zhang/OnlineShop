package com.harson.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.harson.domain.User;
import com.harson.service.UserService;
import com.harson.utils.CommonUtils;
import com.harson.utils.MailUtils;

public class RegisterServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		//获得表单数据
		Map<String, String[]> properties = request.getParameterMap();
		User user = new User();
		CommonUtils.transMap2Bean(properties, user);
		
		//private String uid;
		user.setUid(CommonUtils.getUid());
		//private String telephone;
		user.setTelephone(null);
		//private int state;//是否激活
		user.setState(0);
		//private String code;//激活码
		String activeCode = CommonUtils.getUid();
		user.setCode(activeCode);
		
		
		//将user传递给service层
		UserService service = new UserService();
		boolean isRegisterSuccess = false;
		try {
			isRegisterSuccess = service.register(user);
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
		//是否注册成功
		if(isRegisterSuccess){
			//发送激活邮件
			String emailMsg = "地猫商城恭喜您：注册成功，请点击下面的连接进行激活账户"
					+ "<a href='http://120.78.5.163:8080/OnlineShop/active?activeCode="+activeCode+"'>"
							+ "http://120.78.5.163:8080/OnlineShop/active?activeCode="+activeCode+"</a>";
			try {
				MailUtils.sendMail(user.getEmail(), emailMsg);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			
			
			//跳转到注册成功页面
			response.sendRedirect(request.getContextPath()+"/registerSuccess.jsp");
		}else{
			//跳转到失败的提示页面
			response.sendRedirect(request.getContextPath()+"/registerFail.jsp");
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
