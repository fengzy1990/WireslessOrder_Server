package com.wireless_order_server.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
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
import com.wireless_order_server.dao.UserDao;
import com.wireless_order_server.dao.impl.MenuDaoImpl;
import com.wireless_order_server.dao.impl.UserDaoImpl;
import com.wireless_order_server.entity.MenuBean;
@WebServlet("/upload.do")
public class UploadServlet extends HttpServlet {

	private String uploadPath = ""; // 用于存放上传文件的目录
	private File tempPath = new File("C:\\WirelessOrderTmp\\"); // 用于存放临时文件的目录

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		//request.setCharacterEncoding("utf-8");
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
			MenuDao md = new MenuDaoImpl();
			PrintWriter out = response.getWriter();
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(4096);
			factory.setRepository(tempPath);
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(1000000);
			String menuid = null;
			String typeid = null;
			String name = null;
			String price = null;
			String pic = null;
			String remark = null;
			String tempStr = null;
			
			int t = 0;
			try {
				List fileItems = upload.parseRequest(request);
				Iterator iter = fileItems.iterator();
				//String regExp = ".+\\\\(.+)$"; // 正则匹配，过滤路径取文件名
				//String[] errorType = { ".exe", ".com", ".cgi", ".asp" }; // 过滤掉的文件类型
				//Pattern p = Pattern.compile(regExp);
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					if (item.isFormField()) {
						
						if ("name".equals(item.getFieldName())) {
							name = new String(item.getString().getBytes("iso-8859-1"), "utf-8"); //解决乱码
						} else if ("menutype".equals(item.getFieldName()))
							typeid = item.getString();
						else if ("price".equals(item.getFieldName()))
							price = item.getString();
						else if ("remark".equals(item.getFieldName()))
							remark = new String(item.getString().getBytes("iso-8859-1"), "utf-8");
						else if("menuid".equals(item.getFieldName()))
							menuid = item.getString();

					}
					
					// 忽略其他不是文件域的所有表单信息
					if (!item.isFormField()) {
						String picName = item.getName();
						t = picName.lastIndexOf("\\");
						tempStr = picName.substring(t+1, picName.length());
						System.out.println("tempStr----------------->"+tempStr);
						pic = uploadPath + tempStr;
						long size = item.getSize();
						if ((picName == null || picName.equals(""))
								&& size == 0)
							continue;
						//Matcher m = p.matcher(picName);
						//boolean result = m.find();
						/*if (result) {
							for (int temp = 0; temp < errorType.length; temp++) {
								if (m.group(1).endsWith(errorType[temp])) {
									throw new IOException(name + ": wrong type");
								}
							}*/
							try {

								item.write(new File(pic));

								out.print(name + "&nbsp;&nbsp;" + size
												+ "<br>");
							} catch (Exception e) {
								out.println(e);
							}

						/*} else {
							throw new IOException("fail to upload");
						}*/
					}
				}

			} catch (IOException e) {
				out.println(e);
			} catch (FileUploadException e) {
				out.println(e);
			}
			//System.out.println("name------------>"+name);
			MenuBean mb = new MenuBean();
			mb.setType_id(Integer.parseInt(typeid));
			mb.setName(name);
			mb.setPrice(Integer.parseInt(price));
			mb.setPic(pic);
			mb.setRemark(remark);
			if(method.equals("insert")){  // 执行插入
				boolean b = md.insertMenu(mb);
				if (b) {
					md.listMenu(request, pageSize, pageNo);
					topage = "/menu.jsp";// 跳到列表页
				}
			}else if(method.equals("update")){  // 更新数据
				mb.setMenu_id(Integer.parseInt(menuid));
				boolean b = md.updateMenu(mb);
				if (b) {
					md.listMenu(request, pageSize, pageNo);
					topage = "/menu.jsp";// 跳到列表页
				}
			}
			
			
		}

		// 转发
		RequestDispatcher rd = this.getServletContext().getRequestDispatcher(
				topage);
		rd.forward(request, response);
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		//D:/Tomcat6.0/webapps/WirelessOrder_Server/images/dishes/
		this.uploadPath = "C:/apache-tomcat-6.0.32-windows-x64/apache-tomcat-6.0.32/webapps/WirelessOrder_Server/images/dishes/";
		System.out.println(uploadPath);
	}
}