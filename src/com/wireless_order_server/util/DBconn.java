package com.wireless_order_server.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBconn {

	/**
	 * 鎵撳紑鏁版嵁搴撹繛鎺�
	 * 
	 * @return
	 */
	public Connection getConnection() {
		// 瀹炰緥鍖朠roperties瀵硅薄
		String driver = null;
		String url = null;
		String username = null;
		String password = null;

		try {
			driver = "com.mysql.jdbc.Driver";
			url = "jdbc:mysql://localhost:3306/order_db";
			username = "root";
			password = "DatabaseSql";
			// 娉ㄥ唽椹卞姩
			Class.forName(driver);
			return DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void close(PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 鍏抽棴鏁版嵁搴撹繛鎺�
	 * 
	 * @param conn
	 */
	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}