package com.wireless_order_server.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wireless_order_server.dao.MenuDao;
import com.wireless_order_server.dao.MenuTypeDao;
import com.wireless_order_server.dao.OrderInfoDao;
import com.wireless_order_server.dao.impl.MenuDaoImpl;
import com.wireless_order_server.dao.impl.MenuTypeDaoImpl;
import com.wireless_order_server.dao.impl.OrderInfoDaoImpl;
import com.wireless_order_server.entity.MenuTypeBean;
import com.wireless_order_server.entity.OrderInfoBean;

@WebServlet("/orderinfo.do")
/**
 * 
 * @author FENGYUE
 *
 */
public class SOrderInfoServlet extends HttpServlet {

	public final static long serialVersionUID = 0;
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");// 操作方法
		String orderid = request.getParameter("orderid");
		String topage = "/orderinfo.jsp";// 跳转页地址

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
			OrderInfoDao od = new OrderInfoDaoImpl();
			if (method.equals("list")) {// 列表操作
				// 查询数据
				od.listOrderInfo(request, orderid, pageSize, pageNo);
				topage = "/orderinfo.jsp?orderid=" + orderid;// 跳到列表页
			}

			else if (method.equals("delete")) {// 删除操作

				String id = request.getParameter("id");
				// 执行删除
				boolean b = od.deleteDish(id);
				// 查询数据
				if (b) {
					od.listOrderInfo(request, orderid, pageSize, pageNo);
					topage = "/orderinfo.jsp?orderid=" + orderid;// 跳到列表页
				}

			}

			 else if (method.equals("edit")) {// 修改操作
			 // 执行查询
				 String id = request.getParameter("id");
				 OrderInfoBean ob = od.selectOrderInfo(id);
				 request.setAttribute("orderinfo", ob);
				 MenuTypeDao mtd = new MenuTypeDaoImpl();
				 List<MenuTypeBean> list = mtd.getMenuTypeList();
				 request.setAttribute("list", list);
				 int typeid = od.getMenuTypeId(id);
				 request.setAttribute("typeid", typeid+"");
				 MenuDao md = new MenuDaoImpl();
				 Map map = md.getMenusByTypeid(typeid);
				 request.setAttribute("map", map);
				 topage = "/orderinfo_edit.jsp?orderid=" + orderid+"&id="+id; // 跳到修改页
			 }
			 else if (method.equals("update")) {// 更新操作
				 String menuid = request.getParameter("menu");
				 String disknum = request.getParameter("disknum");
				 String id = request.getParameter("id");
				 OrderInfoBean ob = new OrderInfoBean();
				 ob.setId(Integer.parseInt(id));
				 ob.setOrder_id(Integer.parseInt(orderid));
				 ob.setMenu_id(Integer.parseInt(menuid));
				 ob.setDisknum(Integer.parseInt(disknum));
				 // 更新数据
				 boolean b = od.updateOrderInfo(ob);
				 if(b){
					// 查询数据
					od.listOrderInfo(request, orderid, pageSize, pageNo);
					topage = "/orderinfo.jsp?orderid=" + orderid;// 跳到列表页
				 }
							
			 }
			else if (method.equals("add")) {// 新增操作
				MenuTypeDao mtd = new MenuTypeDaoImpl();
				List<MenuTypeBean> list = mtd.getMenuTypeList();
				request.setAttribute("list", list);
				topage = "/orderinfo_add.jsp?orderid=" + orderid;// 跳到新增页
			} 
			else if (method.equals("insert")) {// 插入操作
				
				String menuid = request.getParameter("menu");
				String disknum = request.getParameter("disknum");
				
				OrderInfoBean ob = new OrderInfoBean();
				ob.setOrder_id(Integer.parseInt(orderid));
				ob.setMenu_id(Integer.parseInt(menuid));
				ob.setDisknum(Integer.parseInt(disknum));
				// 执行插入
				boolean b = od.insertOrderInfo(ob);
				if (b) {
					od.listOrderInfo(request, orderid, pageSize, pageNo);
					topage = "/orderinfo.jsp?orderid=" + orderid;// 跳到列表页
				}

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
