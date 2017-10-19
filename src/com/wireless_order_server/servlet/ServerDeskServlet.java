package com.wireless_order_server.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wireless_order_server.dao.DeskDao;
import com.wireless_order_server.dao.impl.DeskDaoImpl;
import com.wireless_order_server.entity.DeskBean;

@WebServlet("/desk.do")
/**
 * 
 * @author FENGYUE
 *
 */
public class ServerDeskServlet extends HttpServlet {

	public final static long serialVersionUID = 0;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("utf-8");
		String method = " ";
		method = request.getParameter("method");// 操作方法
		String topage = "/desk.jsp";// 跳转页地址

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
			DeskDao dd = new DeskDaoImpl();
			if (method.equals("list")) {// 列表操作
				// 查询数据
				dd.listDesks(request, pageSize, pageNo);
				topage = "/desk.jsp";// 跳到列表页
			}
			// 删除操作
			else if (method.equals("delete")) {

				String deskid = request.getParameter("deskid");
				// 执行删除
				boolean b = dd.deleteDesk(deskid);
				// 查询数据
				if (b) {
					dd.listDesks(request, pageSize, pageNo);
					topage = "/desk.jsp";// 跳到列表页
				}

			} else if (method.equals("edit")) {// 修改操作
				// 执行查询
				String deskid = request.getParameter("deskid");
				DeskBean db = dd.selectDesk(deskid);
				request.setAttribute("desk", db);
				topage = "/desk_edit.jsp";// 跳到修改页
			} else if (method.equals("update")) {// 更新操作

				String deskid = request.getParameter("deskid");
				String flag = request.getParameter("flag");
				String description = request.getParameter("description");
				DeskBean db = new DeskBean();
				db.setDeskId(Integer.parseInt(deskid));
				db.setFlag(Integer.parseInt(flag));
				db.setNum(Integer.parseInt(deskid));
				db.setDescription(description);
				// 更新数据
				boolean b = dd.updateDesk(db);
				if (b) {
					dd.listDesks(request, pageSize, pageNo);
					topage = "/desk.jsp";// 跳到列表页
				}

			}

			// else if (method.equals("add")) {// 新增操作
			// topage = "/users_add.jsp";// 跳到新增页
			// }
		}

		// 转发
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher(topage);
		rd.forward(request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
