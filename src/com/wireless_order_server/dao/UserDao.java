package com.wireless_order_server.dao;

import javax.servlet.http.HttpServletRequest;

import com.wireless_order_server.entity.UserBean;

public interface UserDao {

	public boolean login(String username, String password);

	public boolean insertUser(UserBean u);

	public int getUserPemission(String userid);

	public boolean login(String username, String password, String shenfen);

	public boolean isExist(String userid);

	public boolean listUsers(HttpServletRequest request, String strPageSize,
			String strPageNo);
	
	public boolean deleteUser(String userid);
	
	//public UserBean selectUser(HttpServletRequest request, String userid);
	
	public boolean updateUser(String userid);
}
