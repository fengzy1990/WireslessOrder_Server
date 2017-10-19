package com.wireless_order_server.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wireless_order_server.dao.UserDao;
import com.wireless_order_server.dao.impl.UserDaoImpl;
import com.wireless_order_server.entity.UserBean;
@WebServlet("/servlet/InsertUserServlet")
/**
 * 
 * @author FENGYUE
 *
 */
public class InsertUserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsertUserServlet() {
		super();
	}
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf8");
		response.setContentType("text/html; charset=utf8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		UserBean u = new UserBean();

		String us = request.getParameter("username");
		String pw = request.getParameter("password");
		String na = request.getParameter("name");
		String we = request.getParameter("weight");
		String se = request.getParameter("sex");
		//System.out.print(na);
		System.out.println("weight------------->"+we);
		System.out.println("sex-------------->"+se);
		u.setName(na);
		u.setPassword(pw);
		u.setUser_id(us);
		u.setPermission(Integer.parseInt(we));
		u.setSex(Integer.parseInt(se));
		UserDao d = new UserDaoImpl();
		boolean a = d.insertUser(u);
		out.print(a);
	}

}
