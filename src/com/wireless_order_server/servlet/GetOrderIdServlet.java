package com.wireless_order_server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wireless_order_server.dao.OrderDao;
import com.wireless_order_server.dao.impl.OrderDaoImpl;
import com.wireless_order_server.entity.OrderTbBean;
@WebServlet("/servlet/GetOrderIdServle")

public class GetOrderIdServlet extends HttpServlet {

	public GetOrderIdServlet() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf8");
		response.setCharacterEncoding("utf8");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		OrderTbBean or = new OrderTbBean();
		or.setDesk_id(Integer.parseInt(request.getParameter("num")));
		or.setIsPay(0);
		OrderDao dao = new OrderDaoImpl();
		int a = dao.getOrderid(or);
		System.out.print(a);
		out.print(a);
	}
}
