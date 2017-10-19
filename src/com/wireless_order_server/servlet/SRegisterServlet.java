package com.wireless_order_server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wireless_order_server.dao.UserDao;
import com.wireless_order_server.dao.impl.UserDaoImpl;
import com.wireless_order_server.entity.UserBean;
@WebServlet("/register.do")
public class SRegisterServlet extends HttpServlet {

public final static long serialVersionUID = 0;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		// 取得参数
		String username = request.getParameter("username");
		String password = request.getParameter("password1");
		String name = request.getParameter("name");
		String shenfen = request.getParameter("shenfen");
		String sex = request.getParameter("sex");

		// 注册用户
		UserBean ub = new UserBean();
		UserDao ud = new UserDaoImpl();
		ub.setUser_id(username);
		ub.setPassword(password);
		ub.setName(name);
		ub.setPermission(Integer.parseInt(shenfen));
		ub.setSex(Integer.parseInt(sex));
		boolean isExist = ud.isExist(username);
		if(!isExist) {
			ud.insertUser(ub);
			response.sendRedirect("login.jsp");
		} else {
			response.sendRedirect("register.jsp");
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
