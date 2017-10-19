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
@WebServlet("/servlet/SelectDeskServlet")
public class SelectDeskServlet extends HttpServlet {

	public SelectDeskServlet() {
		super();
	}
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONArray array = new JSONArray();
		DeskDao d = new DeskDaoImpl();
		List<DeskBean> l = d.selectDesk();
		for (int i = 0; i < l.size(); i++) {
			DeskBean bean = (DeskBean) l.get(i);
			JSONObject obj = new JSONObject();
			obj.put("deskid", bean.getDeskId() + "");
			obj.put("flag", bean.getFlag() + "");
			obj.put("num", bean.getNum() + "");
			array.add(obj);
		}
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf8");
		PrintWriter out = response.getWriter();
		out.write(array.toString());
		out.flush();
		out.close();
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
