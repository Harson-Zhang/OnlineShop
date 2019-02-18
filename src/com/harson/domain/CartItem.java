package com.harson.domain;
//购物车中每一项
public class CartItem {
	private Product product;
	private int buyNum; //该商品的购买数量
	private double subtotal; //小计 = product.shop_price * buyNum 
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	
	public CartItem(){
	}
}
