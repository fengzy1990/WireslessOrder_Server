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
@WebServlet("/servlet/DeleteMenuServlet")
public class DeleteMenuServlet extends HttpServlet {

	public DeleteMenuServlet() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("-------------doGet start--------------");
		request.setCharacterEncoding("utf8");
		response.setCharacterEncoding("utf8");
		response.setContentType("text/plain");
		OrderInfoBean or = new OrderInfoBean();
		System.out.println(request.getParameter("orderid"));
		System.out.println(request.getParameter("menuid"));
		or.setOrder_id(Integer.parseInt(request.getParameter("orderid") + ""));
		or.setMenu_id(Integer.parseInt(request.getParameter("menuid") + ""));
		PrintWriter out = response.getWriter();
		OrderInfoDao dao = new OrderInfoDaoImpl();
		boolean a = dao.deleteOrderinfo(or);
		System.out.println(a);
		out.print(a);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
