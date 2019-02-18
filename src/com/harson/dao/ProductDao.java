package com.harson.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.harson.domain.Category;
import com.harson.domain.Order;
import com.harson.domain.OrderItem;
import com.harson.domain.Product;
import com.harson.utils.DataSourceUtils;

public class ProductDao {
	//返回热门商品
	public List<Product> getHotProductList() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM product WHERE is_hot=? AND pflag=? LIMIT ?";
		
		return runner.query(sql, new BeanListHandler<Product>(Product.class), 1, 0, 9);
	}

	//返回最新商品
	public List<Product> getNewProductList() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM product ORDER BY pdate DESC LIMIT ?";
		
		return runner.query(sql, new BeanListHandler<Product>(Product.class), 9);
	}
	
	//返回商品类别
	public List<Category> getCategoryList() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category";
		return runner.query(sql, new BeanListHandler<Category>(Category.class));
	}
	
	//返回指定类型的商品总数
	public int getTotalCountByCid(String cid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		Long query = (Long) runner.query("select count(*) from product where cid = ?",new ScalarHandler() ,cid);
		return query.intValue();
	}

	//根据页号获取商品列表（已经算好起始商品的索引号和查询的商品数）
	public List<Product> getProductListByPage(String cid, int index, int currentCount) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where cid = ? limit ?,?";
		List<Product> query = runner.query(sql, new BeanListHandler<Product>(Product.class), cid, index, currentCount);
		return query;
	}

	public Product getProductByPid(String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid = ?";
		Product query = runner.query(sql, new BeanHandler<Product>(Product.class), pid);
		
		return query;
	}
	
	//将订单存入数据库orders
	public void setOrder(Order order) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into orders values(?,?,?,?,?,?,?,?);";
		Connection conn = DataSourceUtils.getConnection(); //获取与当前线程绑定的connection
		runner.update(conn, sql, order.getOid(), order.getOrdertime(), order.getTotal(), order.getState(),
				order.getAddress(), order.getName(), order.getTelephone(), order.getUser().getUid());
	}
	
	//将订单项存入数据库orderitem
	public void setOrderItems(List<OrderItem> orderItems) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into orderitem values(?,?,?,?,?);";
		Connection conn = DataSourceUtils.getConnection(); //获取与当前线程绑定的connection
		
		//通过循环存入每一条购物项
		for(OrderItem item:orderItems){
			runner.update(conn, sql, item.getItemid(), item.getCount(), item.getSubtotal(),
					item.getProduct().getPid(), item.getOrder().getOid());
		}
	}

	//将订单更新数据库orders
	public int updateOrder(Order order, String oid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update orders set address = ?, name = ?, telephone = ? where oid = ?";
		return runner.update(sql, order.getAddress(), order.getName(), order.getTelephone(), oid);
	}

	//从数据库中选出当前用户所有订单
	public List<Order> getOrders(String uid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders where uid = ?";
		return runner.query(sql, new BeanListHandler<Order>(Order.class), uid);
		
	}

	//根据订单的oid，查询其所有的订单项和商品信息
	public List<Map<String, Object>> getOrderItemsByOid(String oid) throws SQLException{
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select i.itemid, i.count, i.subtotal, p.pimage, p.pname, p.shop_price, p.pid, p.cid "
				+ "from orderitem i, product p "
				+ "where i.pid = p.pid and i.oid = ?";
		//MapListHandler能将查询到的关系表全部返回，每行一个list项。String代表列名，Object为当前行的该列元素
		List<Map<String, Object>> query = runner.query(sql, new MapListHandler(), oid);
		return query;
	}
}
