package com.harson.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.harson.domain.Cart;
import com.harson.domain.CartItem;
//删除单个商品
public class DelProFromCartServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pid = request.getParameter("pid");
		HttpSession session = request.getSession(); 
		Cart cart = (Cart) session.getAttribute("cart"); //从session中取得cart对象
		
		//删除商品，修改总金额
		if(cart != null){
			Map<String, CartItem> cartItems = cart.getCartItems(); //获取购物项
			//修改总金额
			double total = cart.getTotal();
			total -= cartItems.get(pid).getSubtotal(); //计算总价 
			cart.setTotal(total);
			//删除商品
			cartItems.remove(pid);
		}
		
		//放回session
		session.setAttribute("cart", cart);
		
		//转发回去cart.jsp
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
