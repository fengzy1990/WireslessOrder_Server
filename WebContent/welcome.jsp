<%@ page language="java" pageEncoding="UTF-8"%>

<html>
<head>

<title>无线点餐后台管理系统 - 欢迎页面</title>

<link rel="stylesheet" type="text/css" href="css/styles.css">

</head>
<body>
<div align="center">

<table height="100%" width="100%" border="0" cellspacing="0"
	cellpadding="0">
	<tr>
		<td colspan="2" height="108"><%@ include file="inc/top.jsp"%>
		</td>
	</tr>
	<tr>
		<td width="160" bgcolor="#EEEEEE" valign="top" height="100%"><%@ include
			file="inc/menu.jsp"%></td>
		<td align="left" valign="top">
		<TABLE width="100%" class="position">
			<TR>
				<TD>当前位置：首页面</TD>
				<TD width="150">当前用户：<%=session.getAttribute("username")%> </TD>
			</TR>
		</TABLE>
		<br>
		<br>
		<table id="t1" border="0" cellpadding="0" cellspacing="0"
			style="border-collapse: collapse" bordercolor="D0E7FF" width="95%"
			height="2">
			<tr>
				<td align="center"><img src="images/welcome.gif"></td>
			</tr>
			<tr>
				<td align="center">&nbsp;</td>
			</tr>
			<tr>
				<td align="center">&nbsp;</td>
			</tr>
			<tr>
				<td align="center">&nbsp;</td>
			</tr>
			<tr>
				<td align="center">&nbsp;</td>
			</tr>
			<tr>
				<td align="center">
				<h1><font face="华文新魏" color="#FF0099"> 欢迎使用无线点餐后台管理系统</font></h1>
				</td>
			</tr>
			<tr>
				<td align="center">&nbsp;</td>
			</tr>
			<tr>
				<td align="center">&nbsp;</td>
			</tr>
			
		</table>

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
