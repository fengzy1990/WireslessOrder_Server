<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.wireless_order_server.entity.MenuBean"%>
<%@ page import="com.wireless_order_server.entity.MenuTypeBean"%>
<%@ page import="java.util.*"%>
<%
	String pageSize = (String) request.getAttribute("pageSize");
	String pageNo = (String) request.getAttribute("pageNo");
	MenuBean mb = (MenuBean)request.getAttribute("menu");
	
%>
<html>
	<head>

		<title>无线点餐后台管理系统 - 菜单管理</title>

		<link rel="stylesheet" type="text/css" href="css/styles.css">
		<script language="javascript">
			function validMenuEdit(theform) {
				
				var typeid = theform.typeid.value;
				var name = theform.name.value;
				var price = theform.price.value;
				var pic = theform.pic.value;
				var remark = theform.remark.value;
			
				if (typeid == "") {
				    alert("类别不能为空！");
					document.form1.typeid.focus();
					return false;
				}
				if (name == "") {
				    alert("菜名不能为空！");
					document.form1.name.focus();
					return false;
				}
				if (price == "") {
				    alert("单价不能为空！");
					document.form1.price.focus();
					return false;
				}
				if (pic == "") {
				    alert("图片路径不能为空！");
					document.form1.pic.focus();
					return false;
				}
				if (remark == "") {
				    alert("评论不能为空！");
					document.form1.remark.focus();
					return false;
				}
				return true;
			}
		
			
		</script>
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
									当前位置：菜单管理&gt;&gt;修改菜单
								</TD>
								<TD align="right">
									<a
										href="menu.do?method=list&pageSize=<%=pageSize%>&pageNo=<%=pageNo%>">返回菜单信息列表</a>
								</TD>
								<TD width="20"></TD>
							</TR>
						</TABLE>

						<form name="form1" action="upload.do?method=update" ENCTYPE="multipart/form-data" method="post"
							onsubmit="return validMenuEdit(this);">
							<input type="hidden" name="menuid" value="<%=mb.getMenu_id()%>">
							<input type="hidden" name="pageSize" value="<%=pageSize%>">
							<input type="hidden" name="pageNo" value="<%=pageNo%>">

							<TABLE border="0" width="100%">
								
								<TR>
									<TD>
										菜名
									</TD>
									<TD>
										<input type="text" name="name" maxlength="50"
											value="<%=mb.getName()%>">
									</TD>
								</TR>
								
								<TR>
									<TD>
										类别
									</TD>
									<TD>
										<select name="menutype">
											<% 
												List<MenuTypeBean> list = (List)request.getAttribute("list");
												for (Iterator iter=list.iterator(); iter.hasNext();) {
													MenuTypeBean mtb = (MenuTypeBean)iter.next();
													String selectStr = "";
													int typeid = mb.getType_id();
													if (typeid==mtb.getTypeid()) {
														selectStr = "selected";
													}	
											%>
												<option value="<%=mtb.getTypeid() %>" <%=selectStr %>> <%=mtb.getTypename() %></option>
											<%
												} 
												
											%>
									
								
								
										</select>
									</TD>
								</TR>
								<TR>
									<TD>
										单价/元
									</TD>
									<TD>
										<input type="text" name="price" maxlength="20"
											value="<%=mb.getPrice()+""%>">
									</TD>
								</TR>
								<TR>
									<TD>
										图片
									</TD>
									<TD>
										<input name="pic" type="file" maxlength="40"
											>
									</TD>
								</TR>
								<TR>
									<TD>
										评论
									</TD>
									<TD>
										<input type="text" name="remark" maxlength="100"
											value="<%=mb.getRemark()%>">
									</TD>
								</TR>
								<TR>
									<TD colspan="2">
										<input type="submit" value="提交">
									</TD>
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
