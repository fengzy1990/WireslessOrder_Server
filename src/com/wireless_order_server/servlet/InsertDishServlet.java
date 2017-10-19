package com.wireless_order_server.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wireless_order_server.dao.OrderInfoDao;
import com.wireless_order_server.dao.impl.OrderInfoDaoImpl;
import com.wireless_order_server.entity.OrderInfoBean;
@WebServlet("/servlet/InsertDishServlet")
public class InsertDishServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf8");
		response.setContentType("text/html; charset=utf8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		OrderInfoBean or = new OrderInfoBean();
		//System.out.println("disknum------------->"+request.getParameter("disknum"));
		System.out.println("orderid------------->"+request.getParameter("orderid"));
		or.setOrder_id(Integer.parseInt(request.getParameter("orderid")));
		System.out.println("orderid----------->"+request.getParameter("orderid"));
		or.setMenu_id(Integer.parseInt(request.getParameter("menuid")));
		or.setDisknum(Integer.parseInt(request.getParameter("disknum")));
		OrderInfoDao info = new OrderInfoDaoImpl();
		boolean m = info.insertOrdeInfo(or);
		out.print(m);
	}

}
