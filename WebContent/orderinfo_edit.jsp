<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.wireless_order_server.entity.MenuTypeBean"%>
<%@ page import="com.wireless_order_server.entity.OrderInfoBean"%>
<%@ page import="java.util.*"%>

<%
	String pageSize = (String) request.getAttribute("pageSize");
	String pageNo = (String) request.getAttribute("pageNo");
	String orderid = (String) request.getParameter("orderid");
	String typeid = (String) request.getAttribute("typeid");
	OrderInfoBean ob = (OrderInfoBean)request.getAttribute("orderinfo");
	String id = request.getParameter("id");
%>
<html>
	<head>

		<title>无线点餐后台管理系统 - 订单管理</title>

		<link rel="stylesheet" type="text/css" href="css/styles.css">
		<script language="javascript">
			function validOrderInfoAdd(theform) {
				var menu = theform.menu.value;
				var disknum = theform.disknum.value;
				if (menu == "0") {
				    alert("请选择菜名！");
					return false;
				}
				if (disknum == "") {
				    alert("数量不能为空！");
					return false;
				}
				return true;
			}
		
			//---------------------------Ajax begin----------------------------	
			var xmlHttp;
			
			function createXMLHttpRequest() {
				if(window.XMLHttpRequest){ //Mozilla 
						xmlHttp=new XMLHttpRequest();
				}
				else if(window.ActiveXObject){
					try{
						xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
					}catch(e){
						try{
							xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
						}catch(e){}
					}
				}
			}
				
			function callback() {
				if(xmlHttp.readyState == 4) {
					var selectProv = document.getElementById("menu");
					selectProv.options.length = 0;
					var option = new Option("---菜名---", "0");
					selectProv.add(option);
					if(xmlHttp.readyState==4){
					
						if(xmlHttp.status == 200) {
						//alert(xmlHttp.responseXML);
						if(xmlHttp.responseText!=""){
							var xmlDoc = xmlHttp.responseXML.documentElement;
							var xItem = xmlDoc.getElementsByTagName("item");
							for(var i=0; i<xItem.length; i++){
								var id = xItem[i].childNodes[0].firstChild.nodeValue;
								var name = xItem[i].childNodes[1].firstChild.nodeValue;								
								var option = new Option(name, id);
								selectProv.add(option);
								//alert(name);
							}
						}
					}else {
						alert("请求错误，错误码：" + xmlHttp.status);
					}
					}
					
				}
			}		
			
			//---------------------------Ajax end----------------------------		
			function onchangeSelect(menutype){				
				//alert(menutype.value);
				createXMLHttpRequest();				
				var url = "servlet/SelectMenuServlet?typeid=" + menutype.value;			
				xmlHttp.open("GET", url, true);
				xmlHttp.onreadystatechange = callback;
				xmlHttp.send(null);
				
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
									当前位置：订单管理&gt;&gt;修改
								</TD>
								<TD align="right">
									<a
										href="orderinfo.do?method=list&orderid=<%=orderid%>&pageSize=<%=pageSize%>&pageNo=<%=pageNo%>">返回订单详情列表页面</a>
								</TD>
								<TD width="20"></TD>
							</TR>
						</TABLE>
						<form name="form1" action="orderinfo.do?method=update"
							method="post" onsubmit="return validOrderInfoAdd(this);">
							<input type="hidden" name="pageSize" value="<%=pageSize%>">
							<input type="hidden" name="pageNo" value="<%=pageNo%>">
							<input type="hidden" name="id" value="<%=id%>">
							<TABLE border="0" width="100%">
								<TR>
									<TD>
										订单号：
									</TD>
									<TD>
										<input type="text" name="orderid" readonly="true"
											maxlength="50" value="<%=orderid%>">

									</TD>
								</TR>
								<TR>
									<TD>
										类别：
									</TD>
									<TD>
										<select name="menutype" onchange="onchangeSelect(this)">
											<option value="0">
												---类别---
											</option>
											<%
												List<MenuTypeBean> list = (List) request.getAttribute("list");
												for (Iterator iter = list.iterator(); iter.hasNext();) {
													MenuTypeBean mtb = (MenuTypeBean) iter.next();
													String selectStr = "";
													if (new Integer(typeid)==mtb.getTypeid()) {
														selectStr = "selected";
													}	
											%>
											<option value="<%=mtb.getTypeid()%>" <%=selectStr %>>
												<%=mtb.getTypename()%></option>
											<%
												}
											%>
										</select>
									</TD>
								</TR>
								<TR>
									<TD>
										菜名：
									</TD>
									<TD>
										<select name="menu" id="menu">
											<option value="0">
												---菜名---
											</option>
											<%
												Map map = (Map) request.getAttribute("map");
												for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
													Map.Entry entry = (Map.Entry) iter.next();
													String selectStr = "";
													String s = (String)entry.getKey();
													if (Integer.parseInt(s)==ob.getMenu_id()) {
														selectStr = "selected";
													}	
											%>
											<option value="<%=entry.getKey()%>" <%=selectStr %>>
												<%=entry.getValue()%></option>
											<%
												}
											%>
										</select>
									</TD>
								</TR>
								<TR>
									<TD>
										数量：
									</TD>
									<TD>
										<input type="text" name="disknum" maxlength="100" value="<%=ob.getDisknum() %>">
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
