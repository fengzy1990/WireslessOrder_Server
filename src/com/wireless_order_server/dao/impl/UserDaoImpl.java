package com.wireless_order_server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.wireless_order_server.dao.UserDao;
import com.wireless_order_server.entity.UserBean;
import com.wireless_order_server.util.DBconn;

public class UserDaoImpl implements UserDao {

	/**
	 * 前台登陆
	 */
	public boolean login(String username, String password) {
		// TODO Auto-generated method stub
		boolean a = false;
		DBconn dbconn = new DBconn();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbconn.getConnection();
			String sql = "select name from users where user_id=? and password=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				a = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBconn.close(rs);
			DBconn.close(pstmt);
			DBconn.close(conn);
		}
		return a;
	}

	/**
	 * 后台登陆
	 * 
	 * @param username
	 * @param password
	 * @param shenfen
	 * @return
	 */
	public boolean login(String username, String password, String shenfen) {
		// TODO Auto-generated method stub
		boolean a = false;
		DBconn dbconn = new DBconn();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbconn.getConnection();
			String sql = "select name from users where user_id=? and password=? and permission=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setInt(3, Integer.parseInt(shenfen));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				a = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBconn.close(rs);
			DBconn.close(pstmt);
			DBconn.close(conn);
		}
		return a;
	}

	/**
	 * 插入新用户
	 * 
	 * @param u
	 * @return
	 */
	public boolean insertUser(UserBean u) {
		boolean a = false;
		DBconn dbconn = new DBconn();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbconn.getConnection();
			String sql = "insert into users value(?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u.getUser_id());
			pstmt.setString(2, u.getPassword());
			pstmt.setString(3, u.getName());
			pstmt.setInt(4, u.getPermission());
			pstmt.setInt(5, u.getSex());
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
	 * 得到用户的权限
	 * 
	 * @param userid
	 * @return
	 */
	public int getUserPemission(String userid) {
		int p = 0;
		DBconn dbconn = new DBconn();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbconn.getConnection();
			String sql = "select permission from users where user_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				p = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBconn.close(rs);
			DBconn.close(pstmt);
			DBconn.close(conn);
		}
		return p;

	}

	/**
	 * 用户名是否存在
	 * 
	 * @param userid
	 * @return
	 */
	public boolean isExist(String userid) {
		boolean isExist = false;
		DBconn dbconn = new DBconn();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbconn.getConnection();
			String sql = "select * from users where user_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				isExist = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBconn.close(rs);
			DBconn.close(pstmt);
			DBconn.close(conn);
		}
		return isExist;
	}

	/**
	 * 分页显示用户
	 * @param request
	 * @param strPageSize
	 * @param strPageNo
	 * @return
	 */
	public boolean listUsers(HttpServletRequest request, String strPageSize,
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
			String sql = "select count(*) as countall from users";
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
			String sql2 = "select * from users order by name limit ?,?";
			List<Hashtable<String, String>> list = new ArrayList<Hashtable<String, String>>();
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, start);
			pstmt.setInt(2, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// 查询每行数据的各个字段数据
				Hashtable<String, String> hash = new Hashtable<String, String>();				
				hash.put("userid", rs.getString(1));
				hash.put("name", rs.getString(3));
				hash.put("permission", rs.getInt(4)+"");
				hash.put("sex", rs.getInt(5)+"");
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
	 * 删除用户
	 * @param request
	 * @param userid
	 * @return
	 */
	public boolean deleteUser(String userid) {
		// 创建数据库连接
		boolean a = false;
		DBconn dbconn = new DBconn();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbconn.getConnection();
			String sql = "delete from users where user_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
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
	 * 查询用户的信息
	 * @param request
	 * @param userid
	 * @return
	 */
	/*
	public UserBean selectUser(HttpServletRequest request, String userid) {
		// 创建数据库连接
		DBconn dbconn = new DBconn();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserBean ub = new UserBean();
		try {
			conn = dbconn.getConnection();
			String sql = "select * from users where user_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ub.setUser_id(rs.getString(1));
				ub.setName(rs.getString(3));
				ub.setPermission(rs.getInt(4));
				ub.setSex(rs.getInt(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBconn.close(rs);
			DBconn.close(pstmt);
			DBconn.close(conn);
		}
		return ub;
		
	}
	*/
	
	/**
	 * 更新用户的权限
	 */
	public boolean updateUser(String userid) {
		// 创建数据库连接
		boolean a = false;
		DBconn dbconn = new DBconn();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbconn.getConnection();
			String sql = "update users set permission=? where user_id=?";
			pstmt = conn.prepareStatement(sql);
			int p = getUserPemission(userid);
			if(p==0)
				pstmt.setInt(1, 1);
			else
				pstmt.setInt(1, 0);
			pstmt.setString(2, userid);
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
}
