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

import com.wireless_order_server.dao.OrderInfoDao;
import com.wireless_order_server.dao.impl.OrderInfoDaoImpl;
import com.wireless_order_server.entity.OrderBean;
import com.wireless_order_server.entity.OrderInfoBean;
@WebServlet("/servlet/SelectOrderListServlet")
public class SelectOrderListServlet extends HttpServlet {

	public SelectOrderListServlet() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		response.setContentType("text/plain");
		response.setCharacterEncoding("utf8");
		PrintWriter out = response.getWriter();
		OrderInfoBean or = new OrderInfoBean();
		OrderInfoDao info = new OrderInfoDaoImpl();
		or.setOrder_id(Integer.parseInt(request.getParameter("orderid")));
		List<OrderBean> list = info.selectOrder(or);
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			OrderBean bean = (OrderBean) list.get(i);
			JSONObject obj = new JSONObject();
			obj.put("name", bean.getName());
			obj.put("price", bean.getPrice() + "");
			obj.put("disknum", bean.getDisknum() + "");
			obj.put("menuid", bean.getMenu_id() + "");
			array.add(obj);
		}
		out.write(array.toString());
		System.out.print(array.toString());
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
