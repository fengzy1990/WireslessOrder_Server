package com.wireless_order_server.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.wireless_order_server.entity.OrderTbBean;

public interface OrderDao {

	public int insertOrder(OrderTbBean or);
	
	public int getOrderid(OrderTbBean or);
	
	public List<String> getAllOrderid();
	
	public boolean payForOrder(OrderTbBean or);
	
	public boolean listOrders(HttpServletRequest request, String strPageSize,
			String strPageNo);
}
