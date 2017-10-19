package com.wireless_order_server.entity;

/**
 * 订单详情类
 * 
 * @author sxmws
 * 
 */
public class OrderInfoBean {
	
	private int id;
	private int order_id;
	private int menu_id;
	private int disknum;

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}

	public int getDisknum() {
		return disknum;
	}

	public void setDisknum(int disknum) {
		this.disknum = disknum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
