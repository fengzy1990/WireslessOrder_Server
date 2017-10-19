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
@WebServlet("/ajax.do")
public class SAjaxServlet extends HttpServlet {

	public final static long serialVersionUID = 0;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		response.setContentType("text/xml");
		response.setHeader("Cache-Control","no-cache");
		String username = request.getParameter("username");
		UserDao ub = new UserDaoImpl();
		boolean isExist = ub.isExist(username);
		
		PrintWriter out = response.getWriter();
		if(isExist) {
			out.print("<content>failure</content>");
		}else{
			out.print("<content>ok</content>");
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
