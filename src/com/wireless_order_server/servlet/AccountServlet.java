package com.wireless_order_server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wireless_order_server.dao.OrderDao;
import com.wireless_order_server.dao.impl.OrderDaoImpl;
import com.wireless_order_server.entity.OrderTbBean;

@WebServlet("/servlet/AccountServlet")
/**
 * 
 * @author FENGYUE
 *
 */
public class AccountServlet extends HttpServlet {

	public AccountServlet() {
		super();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf8");
		response.setContentType("text/plain");
		request.setCharacterEncoding("utf8");
		PrintWriter out = response.getWriter();
		OrderDao or = new OrderDaoImpl();
		OrderTbBean o = new OrderTbBean();
		o.setOrder_id(Integer.parseInt(request.getParameter("orderid")));
		o.setRemark(request.getParameter("remark"));
		boolean a = or.payForOrder(o);
		// System.out.print(a);
		out.print(a);
	}

}
