package com.wireless_order_server.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wireless_order_server.dao.DeskDao;
import com.wireless_order_server.dao.OrderDao;
import com.wireless_order_server.dao.impl.DeskDaoImpl;
import com.wireless_order_server.dao.impl.OrderDaoImpl;
import com.wireless_order_server.entity.OrderTbBean;
@WebServlet("/servlet/InsertOrderServlet")
public class InsertOrderServlet extends HttpServlet {

	public InsertOrderServlet() {
		super();
	}

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
		DeskDao dd = new DeskDaoImpl();
		int num = Integer.parseInt(request.getParameter("num"));
		int f = dd.selectDeskFlag(num);
		if(f==1){
			out.print(0);
		}else{
			OrderTbBean or = new OrderTbBean();
			or.setOrdertime(request.getParameter("time"));
			//System.out.println("time--------->"+request.getParameter("time"));
			or.setUser_id(request.getParameter("userid"));
			or.setDesk_id(num);
			or.setPeopleNum(Integer.parseInt(request.getParameter("people")));
			or.setIsPay(Integer.parseInt(request.getParameter("isPay")));
			or.setRemark("");
			OrderDao m = new OrderDaoImpl();
			int a = m.insertOrder(or);
			out.print(a);
		}
		
	}

}
