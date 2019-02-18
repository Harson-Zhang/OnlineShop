package com.harson.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.harson.domain.Order;
import com.harson.service.ProductService;
// 从订单详情页跳转过来，用于确认订单（将输入值存入数据库 + 在线支付）
public class ConfirmOrderServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// 存入数据库
		Map<String, String[]> properties = request.getParameterMap();
		String [] oidArray = properties.get("oid");
		String oid = oidArray[0];	//取出oid
		
		Order order = new Order();
		try {
			BeanUtils.populate(order, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		ProductService service = new ProductService();
		
		//向当前oid中存入order信息
		try {
			service.confirmOrder(order, oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	/******************/	
		//在线支付
		//先放一放
		response.sendRedirect(request.getContextPath() + "/order_list");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
