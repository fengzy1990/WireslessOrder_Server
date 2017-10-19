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

/**
 * 
 * @author FENGYUE
 *
 */
public class LoginServlet extends HttpServlet {

	public LoginServlet() {
		super();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		UserDao dao = new UserDaoImpl();
		boolean a = dao.login(username, password);
		System.out.println(username);
		if (a) {
			out.print("1");
		} else {
			out.print("0");
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);

	}

}
