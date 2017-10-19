package com.wireless_order_server.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wireless_order_server.dao.MenuDao;
import com.wireless_order_server.dao.impl.MenuDaoImpl;

@WebServlet("/servlet/SelectMenuServlet")
public class SelectMenuServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SelectMenuServlet() {
		super();
	}
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/xml; charset=UTF-8");
		//response.setCharacterEncoding("utf8");
		//request.setCharacterEncoding("utf8");
		int typeid = Integer.parseInt(request.getParameter("typeid"));

		System.out.println("--------------------->" + typeid);

		if (typeid != 0) {
			MenuDao md = new MenuDaoImpl();
			Map map = md.getMenusByTypeid(typeid);
			StringBuffer sbXml = new StringBuffer();
			sbXml.append("<items>");
			for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
				Map.Entry entry = (Map.Entry) iter.next();
				sbXml.append("<item>").append("<id>").append(entry.getKey())
						.append("</id>").append("<name>").append(
								entry.getValue()).append("</name>").append(
								"</item>");
			}
			sbXml.append("</items>");
			//System.out.println(sbXml.toString());
			response.getWriter().print(sbXml.toString());
		}
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
