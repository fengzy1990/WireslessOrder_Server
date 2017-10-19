package com.wireless_order_server.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wireless_order_server.dao.UserDao;
import com.wireless_order_server.dao.impl.UserDaoImpl;
@WebServlet("/users.do")
public class SUsersServlet extends HttpServlet {

	public final static long serialVersionUID = 0;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		String method = request.getParameter("method");// 操作方法
		String topage = "/users.jsp";// 跳转页地址

		// 未登录时跳转到登录页面
		if (session.getAttribute("username") == null) {
			topage = "/login.jsp";
		} else {
			// 取得分页参数
			String pageSize = request.getParameter("pageSize");// 每页显示行数
			String pageNo = request.getParameter("pageNo");// 当前显示页次
			if (pageSize == null) {// 为空时设置默认页大小为25
				pageSize = "25";
			}
			if (pageNo == null) {// 为空时设置默认为第1页
				pageNo = "1";
			}
			// 保存分页参数，传递给下一个页面
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("pageNo", pageNo);

			// 根据method参数执行各种操作
			UserDao ud = new UserDaoImpl();
			if (method.equals("list")) {// 列表操作
				// 查询数据
				ud.listUsers(request, pageSize, pageNo);
				topage = "/users.jsp";// 跳到列表页
			} else if (method.equals("delete")) {// 删除操作
				// 执行删除
				String userid = request.getParameter("userid");
				ud.deleteUser(userid);
				// 查询数据
				ud.listUsers(request, pageSize, pageNo);
				topage = "/users.jsp";// 跳到列表页
			} else if (method.equals("edit")) {// 修改操作
				// 执行查询
				String userid = request.getParameter("userid");
				boolean p = ud.updateUser(userid);
				if (p) {
					ud.listUsers(request, pageSize, pageNo);
					topage = "/users.jsp";// 跳到列表页
				}
			} else if (method.equals("add")) {// 新增操作
				topage = "/users_add.jsp";// 跳到新增页
			}
		}

		// 转发
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher(
				topage);
		rd.forward(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

}
