package com.wireless_order_server.entity;

/**
 * 显示订单类
 * @author sxmws
 *
 */
public class OrderBean {
	private String name;
	private int price;
	private int disknum;
	private int menu_id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getDisknum() {
		return disknum;
	}

	public void setDisknum(int disknum) {
		this.disknum = disknum;
	}

	public int getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}

}
