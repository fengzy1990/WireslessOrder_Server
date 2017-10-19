package com.wireless_order_server.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.wireless_order_server.entity.DeskBean;

public interface DeskDao {

	public List<DeskBean> selectDeskNum();
	
	public List<DeskBean> selectDesk();
	
	public int selectDeskFlag(int deskid);
	
	public boolean listDesks(HttpServletRequest request, String strPageSize,
			String strPageNo);
	
	public boolean deleteDesk(String deskid);
	
	public DeskBean selectDesk(String deskid);
	
	public boolean updateDesk(DeskBean db);
	
}
