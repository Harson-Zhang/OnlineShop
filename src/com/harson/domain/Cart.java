package com.harson.domain;

import java.util.HashMap;
import java.util.Map;

//购物车类
public class Cart {
	//购物项
	private Map<String, CartItem> cartItems = new HashMap<String, CartItem>();
	//价格总计
	private double total;
	
	public Map<String, CartItem> getCartItems() {
		return cartItems;
	}
	public void setCartItems(Map<String, CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Cart(){
	}
	
}
