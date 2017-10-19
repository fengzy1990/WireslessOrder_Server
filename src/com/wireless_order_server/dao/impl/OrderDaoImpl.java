package com.wireless_order_server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.wireless_order_server.dao.OrderDao;
import com.wireless_order_server.entity.DeskBean;
import com.wireless_order_server.entity.OrderTbBean;
import com.wireless_order_server.util.DBconn;

/**
 * 
 * @author FENGYUE
 *
 */
public class OrderDaoImpl implements OrderDao {

	/**
	 * 添加订单
	 */
	@Override
	public int insertOrder(OrderTbBean or) {
		// TODO Auto-generated method stub
		int a = -1, b = -1, c = -1;
		DBconn db = new DBconn();
		Connection conn = db.getConnection();
		String sql = "insert into ordertb(ordertime,user_id,desk_id,peoplenum,ispay,remark) value(?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, or.getOrdertime());
			pstmt.setString(2, or.getUser_id());
			pstmt.setInt(3, or.getDesk_id());
			pstmt.setInt(4, or.getPeopleNum());
			pstmt.setInt(5, or.getIsPay());
			pstmt.setString(6, or.getRemark());
			b = pstmt.executeUpdate();
			if (b > 0) {
				String sql2 = "select max(order_id) from ordertb";
				pstmt = conn.prepareStatement(sql2);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					a = rs.getInt(1);
				}
				String sql3 = "update desk set flag=1 where desk_id=?";
				pstmt = conn.prepareStatement(sql3);
				pstmt.setInt(1, or.getDesk_id());
				c = pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBconn.close(rs);
			DBconn.close(pstmt);
			DBconn.close(conn);
		}
		return a;
	}

	/**
	 * 查询订单号
	 * 
	 * @param or
	 * @return
	 */
	@Override
	public int getOrderid(OrderTbBean or) {
		int a = -1;
		DBconn db = new DBconn();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = db.getConnection();
			String sql = "select order_id from ordertb where desk_id=? and ispay=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, or.getDesk_id());
			pstmt.setInt(2, or.getIsPay());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				a = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBconn.close(rs);
			DBconn.close(pstmt);
			DBconn.close(conn);
		}
		return a;
	}

	/**
	 * 获得订单号集合
	 * 
	 * @return
	 */
	@Override
	public List<String> getAllOrderid() {
		List<String> l = new ArrayList<String>();
		DBconn db = new DBconn();
		String sql = "select order_id from ordertb";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int a = rs.getInt(1);
				l.add(a + "");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBconn.close(rs);
			DBconn.close(pstmt);
			DBconn.close(conn);
		}
		return l;
	}

	/**
	 * 结算
	 * @param or
	 * @return
	 */
	@Override
	public boolean payForOrder(OrderTbBean or) {
		// TODO Auto-generated method stub
		int a = -1, b = -1, c = -1;
		DBconn db = new DBconn();
		Connection conn = null;
		String sql = "update ordertb set remark=? where order_id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = db.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, or.getRemark());
			pstmt.setInt(2, or.getOrder_id());
			a = pstmt.executeUpdate();
			if (a > 0) {
				String sql2 = "update ordertb set ispay=1 where order_id=?";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, or.getOrder_id());
				b = pstmt.executeUpdate();
				if (b > 0) {
					String sql3 = "update desk set flag=0 where desk_id in(select desk_id from ordertb where order_id=?)";
					pstmt = conn.prepareStatement(sql3);
					pstmt.setInt(1, or.getOrder_id());
					c = pstmt.executeUpdate();
				}

			}
			if(c>0){
				return true;
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBconn.close(rs);
			DBconn.close(pstmt);
			DBconn.close(conn);
		}
		return false;
	}
	
	/**
	 * 查询所有订单
	 * @param request
	 * @param strPageSize
	 * @param strPageNo
	 * @return
	 */
	@Override
	public boolean listOrders(HttpServletRequest request, String strPageSize,
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
			String sql = "select count(*) as countall from ordertb";
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
			String sql2 = "select order_id,desk_id,ordertime,user_id,ispay,remark from ordertb order by order_id limit ?,?";
			List<Hashtable<String, Object>> list = new ArrayList<Hashtable<String, Object>>();
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, start);
			pstmt.setInt(2, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// 查询每行数据的各个字段数据
				Hashtable<String, Object> hash = new Hashtable<String, Object>();				
				hash.put("orderid", rs.getInt(1));
				hash.put("deskid", rs.getInt(2));
				hash.put("ordertime", rs.getTimestamp(3));
				//System.out.println("ordertime--------------->"+ rs.getTimestamp(3));
				hash.put("userid", rs.getString(4));
				hash.put("ispay", rs.getInt(5));
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

}
