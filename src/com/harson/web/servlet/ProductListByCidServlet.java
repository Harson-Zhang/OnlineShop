package com.harson.web.servlet;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harson.domain.PageBean;
import com.harson.domain.Product;
import com.harson.service.ProductService;
//将商品信息按类别进行返回，并进行分页处理
public class ProductListByCidServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid = request.getParameter("cid");
		String currentPageStr = request.getParameter("page");
		int currentPage = 1; 		//当前页
		//判空处理
		if(cid==null){
			cid="1";
		}
		if(currentPageStr!=null){
			currentPage = Integer.parseInt(currentPageStr);
		}
		
		int currentCount = 12;		//每页商品数
		//查询每一页的商品信息，封装到PageBean对象中
		ProductService service = new ProductService();
		PageBean<Product> pageBean = service.getProductListByCid(cid, currentPage, currentCount);
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);
		
		//获取客户端发来的cookie中的历史记录信息，并从数据库中获取浏览的商品
		Cookie[] cookies = request.getCookies();
		String pids="";
		
		List<Product> historyProList = null; //存储访问历史的商品数据
		if(cookies != null){
			for(Cookie cookie:cookies){
				if("pids".equals(cookie.getName())){
					pids = cookie.getValue(); //3-2-1
					String[] split = pids.split("-");  //[3,2,1]
					
					//依据list，从数据库中查询历史记录，放入historyProList列表中
					historyProList = new LinkedList<Product>();
					for(int index=0; index<split.length && index<7; index++ ){
						Product pro_ByPid = service.getProductByPid(split[index]);
						historyProList.add(pro_ByPid);
					}
				}
			}
		}
		request.setAttribute("historyProList", historyProList);
		
		//将PageBean对象传给product_list.jsp
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
