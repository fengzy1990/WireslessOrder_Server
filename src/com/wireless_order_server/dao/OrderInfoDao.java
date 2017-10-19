package com.wireless_order_server.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.wireless_order_server.entity.OrderBean;
import com.wireless_order_server.entity.OrderInfoBean;

public interface OrderInfoDao {

	public boolean insertOrdeInfo(OrderInfoBean or);

	public List<OrderBean> selectOrder(OrderInfoBean o);
	
	public int countOrder(int orderid);
	
	public boolean deleteOrderinfo(OrderInfoBean or);
	
	public boolean listOrderInfo(HttpServletRequest request, String orderid, String strPageSize,
			String strPageNo);
	
	public boolean deleteDish(String id);
	
	public boolean insertOrderInfo(OrderInfoBean ob);
	
	public OrderInfoBean selectOrderInfo(String id);
	
	public int getMenuTypeId(String id);
	
	public boolean updateOrderInfo(OrderInfoBean ob);
}
