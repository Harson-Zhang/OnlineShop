package com.harson.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.harson.dao.ProductDao;
import com.harson.domain.Category;
import com.harson.domain.Order;
import com.harson.domain.PageBean;
import com.harson.domain.Product;
import com.harson.utils.DataSourceUtils;

public class ProductService {

	public List<Product> getHotProductList() {
		ProductDao dao = new ProductDao();
		List<Product> hotProductList = null;
		try {
			hotProductList = dao.getHotProductList();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return hotProductList;
	}

	public List<Product> getNewProductList() {
		ProductDao dao = new ProductDao();
		List<Product> newProductList = null;
		try {
			newProductList = dao.getNewProductList();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return newProductList;
	}

	public List<Category> getCategoryList() {
		ProductDao dao = new ProductDao();
		List<Category> categoryList = null;
		try {
			categoryList = dao.getCategoryList();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return categoryList;
	}

	public PageBean<Product> getProductListByCid(String cid, int currentPage, int currentCount) {
		//封装PageBean
		PageBean<Product> pageBean = new PageBean<Product>();
		ProductDao dao = new ProductDao();
		
		//1. private int currentPage;  当前页 有了
		pageBean.setCurrentPage(currentPage);
		
		//2. private int currentCount; 每页商品数 有了
		pageBean.setCurrentCount(currentCount);
		
		//3. 当前分类的商品总数 private int totalCount;
		int totalCount = 0;
		try {
			totalCount = dao.getTotalCountByCid(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageBean.setTotalCount(totalCount);
		
		//4. 当前分类下的总页数 private int totalPage;
		int totalPage = (int) Math.ceil(1.0*totalCount/currentCount);
		pageBean.setTotalPage(totalPage);
		
		//5. 当前页的商品数据 private List<T> list = new ArrayList<T>();
		int index = currentCount * (currentPage-1);
		List<Product> list = null;
		try {
			list = dao.getProductListByPage(cid, index, currentCount);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		pageBean.setList(list);
		
		return pageBean;
	}

	public Product getProductByPid(String pid) {
		ProductDao dao = new ProductDao();
		Product productByPid = null;
		try {
			productByPid = dao.getProductByPid(pid);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return productByPid;
	}
	//订单传到数据库
	public boolean submitOrder(Order order) {
		ProductDao dao = new ProductDao();
		boolean isSubmitSucceed = true; //订单提交是否成功
		try{
			DataSourceUtils.startTransaction(); //开启事务
			dao.setOrder(order); //存入订单表
			dao.setOrderItems(order.getOrderItems()); //存入订单项表
		}catch(Exception e){
			isSubmitSucceed = false;
			try {
				DataSourceUtils.rollback();	//事务回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				DataSourceUtils.commitAndRelease();  //提交事务
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return isSubmitSucceed;
	}
	
}
