package com.wireless_order_server.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wireless_order_server.entity.MenuBean;

public interface MenuDao {

	public List<MenuBean> selectMenu();
	
	public boolean listMenu(HttpServletRequest request, String strPageSize,
			String strPageNo);
	
	public MenuBean selectMenu(String menuid);
	
	public boolean updateMenu(MenuBean mb);
	
	public boolean insertMenu(MenuBean mb);
	
	public Map<String, String> getMenusByTypeid(int typeid);
}
