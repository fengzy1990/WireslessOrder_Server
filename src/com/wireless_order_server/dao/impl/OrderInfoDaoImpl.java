package com.wireless_order_server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.wireless_order_server.dao.OrderInfoDao;
import com.wireless_order_server.entity.MenuBean;
import com.wireless_order_server.entity.OrderBean;
import com.wireless_order_server.entity.OrderInfoBean;
import com.wireless_order_server.util.DBconn;

public class OrderInfoDaoImpl implements OrderInfoDao {

	/**
	 * 添加一道菜
	 * 
	 * @param or
	 * @return
	 */
	public boolean insertOrdeInfo(OrderInfoBean or) {
		// TODO Auto-generated method stub
		boolean b = false;
		DBconn db = new DBconn();
		Connection conn = db.getConnection();
		PreparedStatement pstmt=null;
		String sql = "insert into orderinfo(order_id,menu_id,disknum) values(?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, or.getOrder_id());
			pstmt.setInt(2, or.getMenu_id());
			pstmt.setInt(3, or.getDisknum());
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
	 * 显示一个订单的详细信息
	 * 
	 * @param o
	 * @return
	 */
	public List<OrderBean> selectOrder(OrderInfoBean o) {
		List<OrderBean> l = new ArrayList<OrderBean>();
		DBconn db = new DBconn();
		Connection conn = db.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql = "select menu.name,menu.price,orderinfo.disknum,menu.menu_id from orderinfo,menu where orderinfo.menu_id=menu.menu_id and order_id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, o.getOrder_id());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				OrderBean or = new OrderBean();
				or.setName(rs.getString(1));
				or.setPrice(rs.getInt(2));
				or.setDisknum(rs.getInt(3));
				or.setMenu_id(rs.getInt(4));
				l.add(or);
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
	 * 总价
	 * @param orderid
	 * @return
	 */
	public int countOrder(int orderid)
	{
		int a=0;
		String sql="select sum(menu.price*orderinfo.disknum) from menu,orderinfo where order_id=? and menu.menu_id=orderinfo.menu_id";
		DBconn db=new DBconn();
		Connection conn=db.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, orderid);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				a=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBconn.close(rs);
			DBconn.close(pstmt);
			DBconn.close(conn);
		}
		
		return a;
	}
	
	/**
	 * 删除一道菜
	 * @param or
	 * @return
	 */
	public boolean deleteOrderinfo(OrderInfoBean or)
	{
		DBconn db=new DBconn();
		Connection conn=db.getConnection();
		PreparedStatement pstmt=null;
		boolean b=false;
		String sql="delete from orderinfo where order_id=? and menu_id=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, or.getOrder_id());
			pstmt.setInt(2, or.getMenu_id());
			int a=pstmt.executeUpdate();
			if(a>0)
			{
				b=true;
			}
			else
			{
				b=false;
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
	 * 分页显示订单详情
	 * @param request
	 * @param orderid
	 * @param strPageSize
	 * @param strPageNo
	 * @return
	 */
	public boolean listOrderInfo(HttpServletRequest request, String orderid, String strPageSize,
			String strPageNo) {
		// 创建数据库连接
		int rowCount = 0, sum = 0;
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
			String sql = "select count(*) as countall from orderinfo where order_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(orderid));
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
			//获得总钱数
			sum = countOrder(Integer.parseInt(orderid));
			request.setAttribute("sum", new Integer(sum));
			// 取得当前页数据SQL
			String sql2 = "select id,order_id,name,price,disknum from orderinfo,menu where order_id=? and orderinfo.menu_id=menu.menu_id limit ?,?";
			List<Hashtable<String, Object>> list = new ArrayList<Hashtable<String, Object>>();
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, Integer.parseInt(orderid));
			pstmt.setInt(2, start);
			pstmt.setInt(3, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// 查询每行数据的各个字段数据
				Hashtable<String, Object> hash = new Hashtable<String, Object>();				
				hash.put("id", rs.getInt(1));
				hash.put("orderid", rs.getInt(2));
				hash.put("name", rs.getString(3));
				hash.put("price", rs.getInt(4));
				hash.put("disknum", rs.getInt(5));
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
	 * 删除一道菜
	 * @param id
	 * @return
	 */
	public boolean deleteDish(String id){
		// 创建数据库连接
		boolean a = false;
		DBconn dbconn = new DBconn();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbconn.getConnection();
			String sql = "delete from orderinfo where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(id));
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
	 * 加一道菜
	 * @param ob
	 * @return
	 */
	public boolean insertOrderInfo(OrderInfoBean ob){
		
		boolean b = false;
		DBconn db = new DBconn();
		Connection conn = db.getConnection();
		PreparedStatement pstmt=null;
		String sql = "insert into orderinfo(order_id,menu_id,disknum) values(?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ob.getOrder_id());
			pstmt.setInt(2, ob.getMenu_id());
			pstmt.setInt(3, ob.getDisknum());
			
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
	 * 根据id查询OrderInfoBean
	 * @param id
	 * @return
	 */
	public OrderInfoBean selectOrderInfo(String id){
		// 创建数据库连接
		DBconn dbconn = new DBconn();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderInfoBean ob = new OrderInfoBean();
		try {
			conn = dbconn.getConnection();
			String sql = "select * from orderinfo where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(id));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ob.setOrder_id(rs.getInt(2));
				ob.setMenu_id(rs.getInt(3));
				ob.setDisknum(rs.getInt(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBconn.close(rs);
			DBconn.close(pstmt);
			DBconn.close(conn);
		}
		return ob;
		
	}
	
	/**
	 * 根据id获得菜类型
	 * @param id
	 * @return
	 */
	public int getMenuTypeId(String id){
		int t = 0;
		DBconn db = new DBconn();
		Connection conn = null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn = db.getConnection();
			String sql = "select type_id from orderinfo,menu where orderinfo.menu_id=menu.menu_id and id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(id));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				t = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBconn.close(rs);
			DBconn.close(pstmt);
			DBconn.close(conn);
		}
		return t;
	}
	
	/**
	 * 更新订单详情
	 * @param ob
	 * @return
	 */
	public boolean updateOrderInfo(OrderInfoBean ob){
		// 创建数据库连接
		boolean a = false;
		DBconn dbconn = new DBconn();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbconn.getConnection();
			String sql = "update orderinfo set menu_id=?,disknum=? where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ob.getMenu_id());
			pstmt.setInt(2, ob.getDisknum());
			pstmt.setInt(3, ob.getId());
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
