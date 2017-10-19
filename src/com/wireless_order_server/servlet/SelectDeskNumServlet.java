package com.wireless_order_server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.wireless_order_server.dao.DeskDao;
import com.wireless_order_server.dao.impl.DeskDaoImpl;
import com.wireless_order_server.entity.DeskBean;
@WebServlet("/servlet/SelectDeskNumServlet")
public class SelectDeskNumServlet extends HttpServlet {

	public SelectDeskNumServlet() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		DeskDao d = new DeskDaoImpl();
		List<DeskBean> list = d.selectDeskNum();
		//System.out.println(list.toString());
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			DeskBean bean = (DeskBean) list.get(i);
			JSONObject obj = new JSONObject();
			obj.put("num", bean.getNum() + "");
			//System.out.print(bean.getNum() + "");
			array.add(obj);
		}
		out.write(array.toString());

		//System.out.print(array.toString());
		out.flush();
		out.close();

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
