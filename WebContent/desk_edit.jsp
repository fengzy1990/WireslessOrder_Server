<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.wireless_order_server.entity.DeskBean"%>
<%
	String pageSize = (String) request.getAttribute("pageSize");
	String pageNo = (String) request.getAttribute("pageNo");
	DeskBean db = (DeskBean)request.getAttribute("desk");
%>
<html>
	<head>

		<title>无线点餐后台管理系统 - 餐桌管理</title>

		<link rel="stylesheet" type="text/css" href="css/styles.css">
		<script language="javascript">
			function validDeskEdit(theform) {
				
				var flag = theform.flag.value;
				var description = theform.description.value;
			
				if (flag == "") {
				    alert("餐桌状态不能为空！");
					document.form1.flag.focus();
					return false;
				}
				if (description == "") {
				    alert("餐桌描述不能为空！");
					document.form1.description.focus();
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
									当前位置：餐桌管理&gt;&gt;修改餐桌
								</TD>
								<TD align="right">
									<a
										href="address.do?method=list&pageSize=<%=pageSize%>&pageNo=<%=pageNo%>">返回餐桌信息列表</a>
								</TD>
								<TD width="20"></TD>
							</TR>
						</TABLE>

						<form name="form1" action="desk.do?method=update" method="post"
							onsubmit="return validDeskEdit(this);">
							<input type="hidden" name="id" value="<%=db.getDesk_id()%>">
							<input type="hidden" name="pageSize" value="<%=pageSize%>">
							<input type="hidden" name="pageNo" value="<%=pageNo%>">

							<TABLE border="0" width="100%">
								<TR>
									<TD>
										桌号
									</TD>
									<TD>
										<input type="text" name="deskid" maxlength="50"
											readonly="true" value="<%=db.getDesk_id()%>">
									</TD>
								</TR>
								<TR>
									<TD>
										餐桌状态
									</TD>
									<TD>
										<input type="text" name="flag" maxlength="10"
											value="<%=db.getFlag()+""%>">
									</TD>
								</TR>
								<TR>
									<TD>
										餐桌描述
									</TD>
									<TD>
										<input type="text" name="description" maxlength="20"
											value="<%=db.getDescription()%>">
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
