package com.wireless_order_server.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.wireless_order_server.dao.MenuDao;
import com.wireless_order_server.dao.MenuTypeDao;
import com.wireless_order_server.dao.impl.MenuDaoImpl;
import com.wireless_order_server.dao.impl.MenuTypeDaoImpl;
import com.wireless_order_server.entity.MenuBean;
import com.wireless_order_server.entity.MenuTypeBean;
@WebServlet("/menu.do")
public class SMenuServlet extends HttpServlet {

	public final static long serialVersionUID = 0;
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");// 操作方法
		String topage = "/menu.jsp";// 跳转页地址

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
			MenuDao md = new MenuDaoImpl();
			if (method.equals("list")) {// 列表操作
				// 查询数据
				md.listMenu(request, pageSize, pageNo);
				topage = "/menu.jsp";// 跳到列表页
			}

			else if (method.equals("edit")) {// 修改操作
				// 执行查询
				String menuid = request.getParameter("menuid");
				MenuBean mb = md.selectMenu(menuid);
				request.setAttribute("menu", mb);
				MenuTypeDao mtd = new MenuTypeDaoImpl();
				List<MenuTypeBean> list = mtd.getMenuTypeList();
				request.setAttribute("list", list);
				topage = "/menu_edit.jsp";// 跳到修改页
			} else if (method.equals("add")) {// 新增操作
				MenuTypeDao mtd = new MenuTypeDaoImpl();
				List<MenuTypeBean> list = mtd.getMenuTypeList();
				request.setAttribute("list", list);
				topage = "/menu_add.jsp";// 跳到新增页
			} 
		}

		// 转发
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher(
				topage);
		rd.forward(request, response);
	}
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	
}
