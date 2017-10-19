package com.wireless_order_server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wireless_order_server.dao.MenuDao;
import com.wireless_order_server.dao.impl.MenuDaoImpl;
import com.wireless_order_server.entity.MenuBean;
@WebServlet("/servlet/UpdateClientMenuServlet")
public class UpdateClientMenuServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UpdateClientMenuServlet() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/xml");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		MenuDao me = new MenuDaoImpl();
		List<MenuBean> list = me.selectMenu();
		// 拼xml文件
		out.println("<?xml version='1.0' encoding='UTF-8'?>");
		out.println("<menulist>");
		for (int i = 0; i < list.size(); i++) {
			MenuBean m = (MenuBean) list.get(i);
			out.println("<menu>");
			// 菜谱编号
			out.print("<id>");
			out.print(m.getMenu_id());
			out.println("</id>");
			// 分类
			out.print("<typeId>");
			out.print(m.getType_id());
			out.println("</typeId>");
			// 名称
			out.print("<name>");
			out.print(m.getName());
			out.println("</name>");
			// 图片路径
			out.print("<pic>");
			out.print(m.getPic());
			out.println("</pic>");
			// 价格
			out.print("<price>");
			out.print(m.getPrice());
			out.println("</price>");
			// 备注
			out.print("<remark>");
			out.print(m.getRemark());
			out.println("</remark>");
			//System.out.println(m.getPic());
			//System.out.println(m.getMenu_id());
			//System.out.println(m.getName());
			//System.out.println(m.getRemark());
			out.println("</menu>");
		}
		out.println("</menulist>");

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
