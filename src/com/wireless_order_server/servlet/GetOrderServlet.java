package com.wireless_order_server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.wireless_order_server.dao.OrderDao;
import com.wireless_order_server.dao.OrderInfoDao;
import com.wireless_order_server.dao.impl.OrderDaoImpl;
import com.wireless_order_server.dao.impl.OrderInfoDaoImpl;
import com.wireless_order_server.entity.SumBean;
@WebServlet("/servlet/GetOrderServlet")
public class GetOrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf8");
		response.setCharacterEncoding("utf8");
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		List<String> list = new ArrayList<String>();
		List<SumBean> list1 = new ArrayList<SumBean>();
		OrderDao or = new OrderDaoImpl();
		list = or.getAllOrderid();
		for (int i = 0; i < list.size(); i++) {
			SumBean b = new SumBean();
			OrderInfoDao dao = new OrderInfoDaoImpl();
			String a = (String) list.get(i);
			int orderid = Integer.parseInt(a);
			b.setPrice(dao.countOrder(orderid));
			b.setOrder_id(orderid);
			list1.add(b);
		}
		JSONArray array = new JSONArray();
		for (int i = 0; i < list1.size(); i++) {
			SumBean bean = (SumBean) list1.get(i);
			JSONObject obj = new JSONObject();
			obj.put("orderid", bean.getOrder_id() + "");
			obj.put("price", bean.getPrice() + "");
			array.add(obj);
		}
		out.write(array.toString());
		System.out.print(array.toString());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
