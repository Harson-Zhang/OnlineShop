package com.harson.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.harson.domain.Category;
import com.harson.service.ProductService;
//获取商品分类信息
public class CategoryListServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取categoryList对象
		ProductService service = new ProductService();
		List<Category> categoryList = service.getCategoryList();
		//使用Gson包将List<Category>转换为json格式
		Gson gson = new Gson();
		String json = gson.toJson(categoryList);
		//发送给ajax
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
