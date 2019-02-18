package com.harson.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.harson.domain.Order;
import com.harson.domain.User;
import com.harson.service.ProductService;
//根据当前用户，获取他的订单
public class GetOrderServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user == null){
			// 请先登录
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		
		// 获取数据库数据，来构建List<Order>
		ProductService service = new ProductService(); 
		List<Order> orderList = null;
		try {
			orderList = service.getOrders(user.getUid());
		} catch (SQLException e) {
			
			e.printStackTrace();
		}	//order内还有user和orderItems没封装
		
		if(orderList != null){	//orderList获取成功，进一步封装
			for (Order order : orderList) {
				order.setUser(user);
				try {
					service.setOrderItemsByOid(order, order.getOid());
				} catch (SQLException e) {
					System.out.println("orderItem封装失败!");
					e.printStackTrace();
				}
			}
		}
		
		//orderList封装完毕，传递给前台
		request.setAttribute("orderList", orderList);
		request.getRequestDispatcher("/order_list.jsp").forward(request, response);;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
