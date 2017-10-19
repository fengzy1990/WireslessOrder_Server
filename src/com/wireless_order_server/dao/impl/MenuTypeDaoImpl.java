package com.wireless_order_server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.wireless_order_server.dao.MenuTypeDao;
import com.wireless_order_server.entity.MenuTypeBean;
import com.wireless_order_server.util.DBconn;

public class MenuTypeDaoImpl implements MenuTypeDao {

	/**
	 * 获得菜谱类型
	 */
	public List<MenuTypeBean> getMenuTypeList() {
		
		List<MenuTypeBean> l=new ArrayList<MenuTypeBean>();
		DBconn db=new DBconn();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select * from menutype";
		try {
			conn=db.getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				MenuTypeBean m=new MenuTypeBean();
				m.setTypeid(rs.getInt(1));
				m.setTypename(rs.getString(2));
				l.add(m);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBconn.close(rs);
			DBconn.close(pstmt);
			DBconn.close(conn);
		}
	
		return l;
	}

}
