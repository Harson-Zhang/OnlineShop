package com.harson.web.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harson.domain.Category;
import com.harson.domain.Product;
import com.harson.service.ProductService;

public class ProductInfoServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//从request中获取参数和cookie
		String cid = request.getParameter("cid");
		String page = request.getParameter("page");
		String pid = request.getParameter("pid");
		Cookie[] cookies = request.getCookies();
		
		//判空
		if(page==null){
			page = "1";
		}
		if(cid==null){
			cid = "1";
		}
		
		
		//将pid传到service层，并返回product对象
		ProductService service = new ProductService();
		Product productByPid = service.getProductByPid(pid);
		String cname = "分类";
		List<Category> cateList = service.getCategoryList();
		for(Category ele:cateList){
			if(cid.equals(ele.getCid()))
				cname = ele.getCname();
		}
		
		//将product对象和其他。。放入request中，转发给product_info
		request.setAttribute("cid", cid);
		request.setAttribute("cname", cname);
		request.setAttribute("page", page);
		request.setAttribute("productByPid", productByPid);
		
		//从客户端的cookie中取出数据 --- 获得名字是pids的cookie
		String pids = pid; //给pids先赋值，防止第一次访问pids为空的情况
				
		if(cookies!=null){
			for(Cookie cookie : cookies){
			    if(cookie.getName().equals("pids")){
			    	//若存在pids的cookie，则提取出其中所有的pid，用于查询历史记录
			    	//并将pids与这次访问的商品pid进行拼接，用于发送给客户端
			    	/*pids格式如下，先访问的排在后面：
			    	 * 3-2-1 	顺序：1,2,本次访问3
			    	 * 2-3-1 	 顺序：1,2,3,本次访问2
			    	 */
			        pids = cookie.getValue(); 
			        String[] split = pids.split("-");  //[3,2,1]
			        //将split转化为List，便于调用相关方法
			        List<String> asList = Arrays.asList(split);
			        LinkedList<String> list = new LinkedList<String>(asList);
			        //加入当前访问商品的pid
			        //若当前商品pid出现在pids中，则调整到最前面
			        if(list.contains(pid)){
			        	list.remove(pid);
			        }
			        list.addFirst(pid);	//[2,3,1]
			        
			        //将list拼接为字符串，当做发送给客户端的cookie
			        StringBuilder sb = new StringBuilder();
			        for(String pro_id:list){
			        	sb.append(pro_id);
			        	sb.append("-");
			        }
			        pids = sb.substring(0, sb.length()-1); //去掉多余的“-”,得到：2-3-1
			    }
			}
		}
		
		//转发之前要创建cookie存储pid，并发送给客户端
		Cookie cookie_pid = new Cookie("pids", pids);
		response.addCookie(cookie_pid);
		
		//转发
		request.getRequestDispatcher("/product_info.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
