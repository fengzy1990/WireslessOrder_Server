package com.wireless_order_server.entity;

/**
 * 餐桌类
 * @author sxmws
 *
 */
public class DeskBean {

	private int desk_id;
	private int flag;
	private int num;
	private String description;

	public int getDesk_id() {
		return desk_id;
	}

	public void setDesk_id(int desk_id) {
		this.desk_id = desk_id;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
