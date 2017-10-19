package com.wireless_order_server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.wireless_order_server.dao.DeskDao;
import com.wireless_order_server.entity.DeskBean;
import com.wireless_order_server.util.DBconn;

public class DeskDaoImpl implements DeskDao {

	/**
	 * 查询空桌号
	 */
	public List<DeskBean> selectDeskNum()
	{
		List<DeskBean> list=new ArrayList<DeskBean>();
		String sql="select num from desk where flag=0";
		DBconn db=new DBconn();
		Connection conn=db.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				DeskBean d=new DeskBean();
				d.setNum(rs.getInt(1));
				list.add(d);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBconn.close(rs);
			DBconn.close(pstmt);
			DBconn.close(conn);
		}
		return list;
	}

	/**
	 * 查询所有的餐桌信息
	 * @return
	 */
	public List<DeskBean> selectDesk()
	{
		List<DeskBean> l=new ArrayList<DeskBean>();
		DBconn db=new DBconn();
		Connection conn=db.getConnection();
		String sql="select * from desk";
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				DeskBean d=new DeskBean();
				d.setDesk_id(rs.getInt(1));
				d.setFlag(rs.getInt(2));
				d.setNum(rs.getInt(3));
				l.add(d);
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
	 * 查询当前餐桌的状态
	 * @param deskid
	 * @return
	 */
	public int selectDeskFlag(int deskid)
	{
		int f = 0;
		DBconn db=new DBconn();
		String sql="select flag from desk where desk_id=?";
		Connection conn=null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		try {
			conn=db.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, deskid);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				f = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBconn.close(rs);
			DBconn.close(pstmt);
			DBconn.close(conn);
		}
		
		return f;
	}
	
	/**
	 * 分页显示餐桌信息
	 * @param request
	 * @param strPageSize
	 * @param strPageNo
	 * @return
	 */
	public boolean listDesks(HttpServletRequest request, String strPageSize,
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
			String sql = "select count(*) as countall from desk";
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
			String sql2 = "select * from desk order by desk_id limit ?,?";
			List<Hashtable<String, String>> list = new ArrayList<Hashtable<String, String>>();
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, start);
			pstmt.setInt(2, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// 查询每行数据的各个字段数据
				Hashtable<String, String> hash = new Hashtable<String, String>();				
				hash.put("deskid", rs.getInt(1)+"");
				hash.put("flag", rs.getInt(2)+"");
				hash.put("description", rs.getString(4));
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
	 * 删除餐桌
	 * @param deskid
	 * @return
	 */
	public boolean deleteDesk(String deskid) {
		// 创建数据库连接
		boolean a = false;
		DBconn dbconn = new DBconn();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbconn.getConnection();
			String sql = "delete from desk where desk_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(deskid));
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
	 * 查询餐桌
	 * @param deskid
	 * @return
	 */
	public DeskBean selectDesk(String deskid) {
		// 创建数据库连接
		DBconn dbconn = new DBconn();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DeskBean db = new DeskBean();
		try {
			conn = dbconn.getConnection();
			String sql = "select * from desk where desk_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(deskid));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				db.setDesk_id(rs.getInt(1));
				db.setFlag(rs.getInt(2));
				db.setNum(rs.getInt(3));
				db.setDescription(rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBconn.close(rs);
			DBconn.close(pstmt);
			DBconn.close(conn);
		}
		return db;
		
	}

	/**
	 * 更新
	 * @param db
	 * @return
	 */
	public boolean updateDesk(DeskBean db) {
		// 创建数据库连接
		boolean a = false;
		DBconn dbconn = new DBconn();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbconn.getConnection();
			String sql = "update desk set flag=?,description=? where desk_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, db.getFlag());
			pstmt.setString(2, db.getDescription());
			pstmt.setInt(3, db.getDesk_id());
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
