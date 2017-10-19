<%@ page language="java" pageEncoding="UTF-8"%>

<%@page import="java.util.*"%>
<%@ page import="java.text.*" %>  

<%
	String pageSize = (String) request.getAttribute("pageSize");
	String pageNo = (String) request.getAttribute("pageNo");
%>
<html>
	<head>

		<title>无线点餐后台管理系统 - 订单管理</title>

		<link rel="stylesheet" type="text/css" href="css/styles.css">

	</head>
	<body>
		<div align="center">

			<table height="100%" width="100%" border="0" cellspacing="0"
				cellpadding="0">
				<tr>
					<td colspan="2" height="108">
						<table height="108" background="images/banner.jpg" border="0"
							cellspacing="0" cellpadding="0" width="100%">
							<tr>
								<td></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td width="160" bgcolor="#EEEEEE" valign="top" height="100%"><%@ include
							file="inc/menu.jsp"%></td>
					<td align="left" valign="top">

						<TABLE width="100%" class="position">
							<TR>
								<TD>
									当前位置：订单管理
								</TD>
								<TD align="right">
									
								</TD>
								<TD width="20"></TD>
							</TR>
						</TABLE>
						<b></b>

						<TABLE border="0" width="100%">

							<TR class="tableheader">
								<TD>
									订单号
								</TD>
								<TD>
									桌号
								</TD>
								<TD>
									订单时间
								</TD>
								<TD>
									服务员
								</TD>
								<TD>
									是否付款
								</TD>
								<TD>
									评论
								</TD>
						
							</TR>

							<%
								List<Hashtable<String, Object>> list = (List<Hashtable<String, Object>>) request.getAttribute("list");
								Iterator it = list.iterator();
								while (it.hasNext()) {
									Hashtable<String, Object> hash = (Hashtable<String, Object>) it.next();
									Integer orderid = (Integer)hash.get("orderid");
							%>
							<TR>
								<TD><a href='orderinfo.do?method=list&orderid=<%=orderid%>'><%=orderid%></a></TD>
								<TD><%=hash.get("deskid")%></TD>
								<TD><%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(hash.get("ordertime"))%></TD>
								<TD><%=hash.get("userid")%></TD>
								<TD><%=(new Integer(0).equals(hash.get("ispay"))?"未付款":"已付款")%></TD>
								<TD><%=hash.get("remark")%></TD>
							
							</TR>
							<%
								}
							%>
						</TABLE>


						<form name="form1" action="order.do?method=list" method="post">
							<TABLE border="0" width="100%" class="pager">
								<TR>
									<TD align="left">
										每页记录数：
										<select name="pageSize"
											onchange="document.all.pageNo.value='1';document.all.form1.submit();">
											<option value="10" <%if(pageSize.equals("10")){%>
												selected="selected" <%}%>>
												10
											</option>
											<option value="25" <%if(pageSize.equals("25")){%>
												selected="selected" <%}%>>
												25
											</option>
											<option value="50" <%if(pageSize.equals("50")){%>
												selected="selected" <%}%>>
												50
											</option>
											<option value="100" <%if(pageSize.equals("100")){%>
												selected="selected" <%}%>>
												100
											</option>
											<option value="200" <%if(pageSize.equals("200")){%>
												selected="selected" <%}%>>
												200
											</option>
											<option value="300" <%if(pageSize.equals("300")){%>
												selected="selected" <%}%>>
												300
											</option>
											<option value="500" <%if(pageSize.equals("500")){%>
												selected="selected" <%}%>>
												500
											</option>
										</select>
									</TD>
									<TD align="center">
										总记录数：<%=request.getAttribute("rowCount")%></TD>
									<TD align="right">
										<a
											href="javascript:document.all.pageNo.value='<%=request.getAttribute("pageFirstNo")%>';document.all.form1.submit();">首页</a>
										<a
											href="javascript:document.all.pageNo.value='<%=request.getAttribute("pagePreNo")%>';document.all.form1.submit();">前一页</a>
										<a
											href="javascript:document.all.pageNo.value='<%=request.getAttribute("pageNextNo")%>';document.all.form1.submit();">后一页</a>
										<a
											href="javascript:document.all.pageNo.value='<%=request.getAttribute("pageLastNo")%>';document.all.form1.submit();">尾页</a>
										<select name="pageNo" onchange="document.all.form1.submit();">
											<%
												int pageCount = ((Integer) request.getAttribute("pageCount"))
														.intValue();
												for (int i = 1; i <= pageCount; i++) {
											%>
											<option value="<%=i%>" <%if(pageNo.equals(i+"")){%>
												selected="selected" <%}%>><%=i%></option>
											<%
												}
											%>
										</select>
									</TD>
									<TD width="20"></TD>
								</TR>
							</TABLE>
						</form>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center"><%@ include file="inc/foot.jsp"%>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
