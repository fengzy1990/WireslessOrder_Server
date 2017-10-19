package com.wireless_order_server.entity;

/**
 * 订单类
 */
import java.util.Date;

public class OrderTbBean {
	
	private int order_id;
	private String ordertime;
	private String user_id;
	private int desk_id;
	private int peopleNum;
	private int isPay;
	private String remark;

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public String getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getDesk_id() {
		return desk_id;
	}

	public void setDesk_id(int desk_id) {
		this.desk_id = desk_id;
	}

	public int getPeopleNum() {
		return peopleNum;
	}

	public void setPeopleNum(int peopleNum) {
		this.peopleNum = peopleNum;
	}

	public int getIsPay() {
		return isPay;
	}

	public void setIsPay(int isPay) {
		this.isPay = isPay;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
