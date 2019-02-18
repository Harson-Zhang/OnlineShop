package com.harson.web.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.harson.domain.Cart;
import com.harson.domain.CartItem;
import com.harson.domain.Order;
import com.harson.domain.OrderItem;
import com.harson.domain.User;
import com.harson.service.ProductService;
import com.harson.utils.CommonUtils;
//提交订单，将订单存入数据库
public class SubmitOrderServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		User user = (User) session.getAttribute("user");
		if(cart!=null && user!=null){ //确认用户登录，购物车还在
			//取出数据，封装成order，以便传到service层，存入数据库
			Order order = new Order();

			//1、private String oid;//该订单的订单号
			String oid = CommonUtils.getUid();
			order.setOid(oid);

			//2、private Date ordertime;//下单时间
			order.setOrdertime(new Date());

			//3、private double total;//该订单的总金额
			//获得session中的购物车
			double total = cart.getTotal();
			order.setTotal(total);

			//4、private int state;//订单支付状态 1代表已付款 0代表未付款
			order.setState(0);

			//5、private String address;//收货地址
			order.setAddress(null);

			//6、private String name;//收货人
			order.setName(null);

			//7、private String telephone;//收货人电话
			order.setTelephone(null);

			//8、private User user;//该订单属于哪个用户
			order.setUser(user);

			//9、该订单中有多少订单项List<OrderItem> orderItems = new ArrayList<OrderItem>();
			//获得购物车中的购物项的集合map
			Map<String, CartItem> cartItems = cart.getCartItems();
			for(Map.Entry<String, CartItem> entry : cartItems.entrySet()){
				//取出每一个购物项
				CartItem cartItem = entry.getValue();
				//创建新的订单项
				OrderItem orderItem = new OrderItem();
				//1)private String itemid;//订单项的id
				orderItem.setItemid(CommonUtils.getUid());
				//2)private int count;//订单项内商品的购买数量
				orderItem.setCount(cartItem.getBuyNum());
				//3)private double subtotal;//订单项小计
				orderItem.setSubtotal(cartItem.getSubtotal());
				//4)private Product product;//订单项内部的商品
				orderItem.setProduct(cartItem.getProduct());
				//5)private Order order;//该订单项属于哪个订单
				orderItem.setOrder(order);

				//将该订单项添加到订单的订单项集合中
				order.getOrderItems().add(orderItem);
			}
			
			//至此，order已经封装完毕！
			//发射到service层吧！
			ProductService service = new ProductService();
			boolean isSubmitSucceed = service.submitOrder(order);
			if(isSubmitSucceed){
				session.setAttribute("order", order); //order放入session域，准备在前台显示订单
			}
			response.sendRedirect(request.getContextPath() + "/order_info.jsp");
		}
		//response.sendError(233);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
