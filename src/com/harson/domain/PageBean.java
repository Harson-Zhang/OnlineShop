package com.harson.domain;

import java.util.ArrayList;
import java.util.List;

public class PageBean<T> {
	//当前页的页号
	private int currentPage;
	//每页显示的商品数
	private int currentCount;
	//商品的总数
	private int totalCount;
	//总页数
	private int totalPage;
	//当前页的商品数据
	private List<T> list = new ArrayList<T>();
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	
}
