package com.wireless_order_server.entity;

/**
 * 用户类
 * @author sxmws
 *
 */
public class UserBean {
	
	private String user_id;
	private String password;
	private String name;
	private int permission;
	private int sex;
	
	public UserBean(){}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	};
	
	
}
