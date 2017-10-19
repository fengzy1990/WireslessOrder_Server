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

@WebServlet("/servlet/CountOrderServlet")
/**
 * 
 * @author FENGYUE
 *
 */
public class CountOrderServlet extends HttpServlet {

	public CountOrderServlet() {
		super();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf8");
		response.setContentType("text/plain");
		request.setCharacterEncoding("utf8");
		PrintWriter out = response.getWriter();
		OrderInfoDao or = new OrderInfoDaoImpl();
		OrderInfoBean o = new OrderInfoBean();
		o.setOrder_id(Integer.parseInt(request.getParameter("orderid")));
		int a = or.countOrder(o.getOrder_id());
		System.out.print(a);
		out.print(a + "");
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
