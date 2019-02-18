package com.harson.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.harson.domain.Cart;
import com.harson.domain.CartItem;
import com.harson.domain.Product;
import com.harson.service.ProductService;
//商品加入购物车
public class AddProductToCartServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String pid = request.getParameter("pid");	//商品ID
		int buyNum = Integer.parseInt(request.getParameter("buyNum"));	//商品购买数量
		HttpSession session = request.getSession(); //获取Session，用于存入购物车对象
		ProductService service = new ProductService();
		
		//1. 安排好cartItems
		//查找当前session中是否存在cart对象
			//没有就new一个
			//有cart对象的话看看里面是否有pid对应的cartItem
				//有的话将这次的buyNum加上，并更新小计subTotal = product.getShop_price() * 加完之后的buyNum
				//没有的话
			
			//从数据库中找到Product对象，把其他数据一起封装到一个新的cartItem对象中
		
		//2. 计算当前cart的总价，封装到cart对象中
		
		//3. 将cart对象放入session域中
		
		//4. 转发到cart.jsp
		
		Cart cart = (Cart) session.getAttribute("cart");
		Map<String, CartItem> cartItems = new HashMap<>(); //先创建一个cartItems对象，留以后用
		CartItem item = null;
		double total = 0.0;
		
		if(cart==null){
			//一种情况是cart根本没有，需要新建cart和item
			cart = new Cart();
			item = new CartItem();
			
			item.setBuyNum(buyNum);
			Product product = service.getProductByPid(pid);
			item.setProduct(product);
			total = buyNum * product.getShop_price(); //小计就与总计相等了。。。
			item.setSubtotal(total);
			
		}else{
			cartItems = cart.getCartItems();
			total = cart.getTotal();
			double subtotal = 0.0;
			
			if(cartItems.containsKey(pid)){
				//获得购物车项
				item = cartItems.get(pid);
				
				int oldBuyNum = cartItems.get(pid).getBuyNum(); //获得原先购物车里该商品的数量
				int newBuyNum = oldBuyNum + buyNum; //更新商品数量
				subtotal = newBuyNum * item.getProduct().getShop_price(); //更新小计
				total += buyNum * item.getProduct().getShop_price(); //更新总计
				
				//向该购物车项中封装数据
				item.setBuyNum(newBuyNum);
				item.setSubtotal(subtotal);

				//至此，重复的cartItem数据准备完毕
			}else{
				//一种情况是pid没重复的商品，需要新建cartItem
				item = new CartItem();
				item.setBuyNum(buyNum);
				Product product = service.getProductByPid(pid);
				item.setProduct(product);
				item.setSubtotal(buyNum * product.getShop_price());
				
				total += buyNum * product.getShop_price(); //更新总计
			}
		}
		
		//将item放回与pid对应的cartItems中，再将cartItems放回购物车cart中
		cartItems.put(pid, item);
		cart.setCartItems(cartItems);

		//计算好的cart的总价，封装到cart对象中
		cart.setTotal(total);
		
		//将购物车再次放入session
		session.setAttribute("cart", cart);
		
		response.setContentType("text/html;charset=UTF-8");
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
