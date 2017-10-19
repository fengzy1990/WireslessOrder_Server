package com.wireless_order_server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.wireless_order_server.dao.MenuDao;
import com.wireless_order_server.entity.MenuBean;
import com.wireless_order_server.util.DBconn;
/**
 * 
 * @author FENGYUE
 *
 */
public class MenuDaoImpl implements MenuDao {

	/**
	 * 查询菜单
	 * @return
	 */
	@Override
	public List<MenuBean> selectMenu()
	{
		List<MenuBean> l=new ArrayList<MenuBean>();
		DBconn db=new DBconn();
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select menu_id,type_id,name,price,pic,remark from menu";
		try {
			conn=db.getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				MenuBean m=new MenuBean();
				m.setMenu_id(rs.getInt(1));
				m.setType_id(rs.getInt(2));
				m.setName(rs.getString(3));
				m.setPrice(rs.getInt(4));
				m.setPic(rs.getString(5));
				m.setRemark(rs.getString(6));
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
	
	/**
	 * 分页显示菜谱
	 * @param request
	 * @param strPageSize
	 * @param strPageNo
	 * @return
	 */
	@Override
	public boolean listMenu(HttpServletRequest request, String strPageSize,
			String strPageNo) {
		// 创建数据库连接
		int rowCount = 0;
		DBconn dbconn = new DBconn();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 查询总的记录数，计算跳页参数
		int pageSize = Integer.parseInt(strPageSize);
		int pageNo = Integer.parseInt(strPageNo);
		int start = pageSize * (pageNo - 1);
		try {
			conn = dbconn.getConnection();
			String sql = "select count(*) as countall from menu";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				rowCount = rs.getInt(1);
			}
			request.setAttribute("rowCount", new Integer(rowCount));
			// 计算总页数并保存
			int pageCount = rowCount % pageSize == 0 ? rowCount / pageSize
					: rowCount / pageSize + 1;
			request.setAttribute("pageCount", new Integer(pageCount));
			// 计算跳页参数并保存
			int pageFirstNo = 1;// 首页
			int pageLastNo = pageCount;// 尾页
			int pagePreNo = pageNo > 1 ? pageNo - 1 : 1;// 前一页
			int pageNextNo = pageNo < pageCount ? pageNo + 1 : pageCount;// 后一页
			request.setAttribute("pageFirstNo", new Integer(pageFirstNo));
			request.setAttribute("pageLastNo", new Integer(pageLastNo));
			request.setAttribute("pagePreNo", new Integer(pagePreNo));
			request.setAttribute("pageNextNo", new Integer(pageNextNo));
			// 取得当前页数据SQL
			String sql2 = "select menu_id,type_name,name,price,pic,remark from menu,menutype where menu.type_id=menutype.type_id order by menu.type_id limit ?,?";
			List<Hashtable<String, String>> list = new ArrayList<Hashtable<String, String>>();
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, start);
			pstmt.setInt(2, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// 查询每行数据的各个字段数据
				Hashtable<String, String> hash = new Hashtable<String, String>();				
				hash.put("menuid", rs.getInt(1)+"");
				hash.put("typename", rs.getString(2));
				hash.put("name", rs.getString(3));
				hash.put("price", rs.getInt(4)+"");
				hash.put("pic", rs.getString(5));
				hash.put("remark", rs.getString(6));
				list.add(hash);
			}
			// 保存所有行数据列表传递给下一个页面
			request.setAttribute("list", list);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBconn.close(rs);
			DBconn.close(pstmt);
			DBconn.close(conn);
		}
		return true;
	}
	
	/**
	 * 查询menuid
	 * @param menuid
	 * @return
	 */
	@Override
	public MenuBean selectMenu(String menuid) {
		// 创建数据库连接
		DBconn dbconn = new DBconn();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MenuBean mb = new MenuBean();
		try {
			conn = dbconn.getConnection();
			String sql = "select * from menu where menu_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(menuid));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				mb.setMenu_id(rs.getInt(1));
				mb.setType_id(rs.getInt(2));
				mb.setName(rs.getString(3));
				mb.setPrice(rs.getInt(4));
				mb.setPic(rs.getString(5));
				mb.setRemark(rs.getString(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBconn.close(rs);
			DBconn.close(pstmt);
			DBconn.close(conn);
		}
		return mb;
		
	}
	
	/**
	 * 更新菜单
	 * @param mb
	 * @return
	 */
	@Override
	public boolean updateMenu(MenuBean mb) {
		// 创建数据库连接
		boolean a = false;
		DBconn dbconn = new DBconn();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbconn.getConnection();
			String sql = "update menu set type_id=?,name=?,price=?,pic=?,remark=? where menu_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mb.getType_id());
			pstmt.setString(2, mb.getName());
			pstmt.setInt(3, mb.getPrice());
			pstmt.setString(4, mb.getPic());
			pstmt.setString(5, mb.getRemark());
			pstmt.setInt(6, mb.getMenu_id());
			int b = pstmt.executeUpdate();
			if (b > 0) {
				a = true;
			} else {
				a = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			DBconn.close(pstmt);
			DBconn.close(conn);
		}
		return a;
	}
	
	/**
	 * 加菜单
	 * @param mb
	 * @return
	 */
	@Override
	public boolean insertMenu(MenuBean mb){
		boolean b = false;
		DBconn db = new DBconn();
		Connection conn = db.getConnection();
		PreparedStatement pstmt=null;
		String sql = "insert into menu(type_id,name,price,pic,remark) values(?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mb.getType_id());
			pstmt.setString(2, mb.getName());
			pstmt.setInt(3, mb.getPrice());
			pstmt.setString(4, mb.getPic());
			pstmt.setString(5, mb.getRemark());
			int a = pstmt.executeUpdate();
			if (a > 0) {
				b = true;
			} else {
				b = false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBconn.close(pstmt);
			DBconn.close(conn);
		}
		return b;
	}
	
	/**
	 * 根据typeid查询菜单
	 * @param typeid
	 * @return
	 */
	@Override
	public Map getMenusByTypeid(int typeid) {
		DBconn db = new DBconn();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		Map map = new HashMap();
		try {
			conn = db.getConnection();
			String sql = "select menu_id, name from menu where type_id=? order by menu_id";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, typeid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				map.put(rs.getInt(1)+"", rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBconn.close(rs);
			DBconn.close(pstmt);
			DBconn.close(conn);
		}
		return map;
	}
}
