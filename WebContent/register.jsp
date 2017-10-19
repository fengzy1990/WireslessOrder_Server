<%@ page language="java" pageEncoding="UTF-8"%>
<html>
	<head>

		<title>无线点餐后台管理系统 - 注册</title>

		<link rel="stylesheet" type="text/css" href="css/styles.css">
		<script language="javascript">
			function validRegister(theform) {
				var username = theform.username.value;
				var password1 = theform.password1.value;
				var password2 = theform.password2.value;
				var name = theform.name.value;
				if (username == "") {
				    alert("用户名不能为空！");
					document.form1.username.focus();
					return false;
				}
				if (password1 == "") {
				    alert("密码不能为空！");
					document.form1.password1.focus();
					return false;
				}
				if (password1 != password2) {
				    alert("两个密码不相等！");
					document.form1.password2.focus();
					return false;
				}
				if (name == "") {
				    alert("真实姓名不能为空！");
					document.form1.name.focus();
					return false;
				}
				if(!checkSex()){
					alert('你选择你的性别!');
					//document.form1.shenfen.focus();
					return false;
				}
				return true;
			}	
			
			function checkSex(){
				var sex = document.getElementsByName("sex");
				var count = 0;
				for (var i=0; i<sex.length; i++) {
					if (sex[i].checked) {
						count++;
						break;
					}
				}
					if (count == 0) {
						return false;
					}
					return true;
			}
		</script>
		<script>

	//--------------------Ajax begin--------------------
	
			var XMLHttpReq;
			
			//创建一个XMLHttpRequest对象
			function createXMLHttpRequest(){
				if(window.XMLHttpRequest){ //Mozilla 
						XMLHttpReq=new XMLHttpRequest();
				}
				else if(window.ActiveXObject){
					try{
						XMLHttpReq=new ActiveXObject("Msxml2.XMLHTTP");
					}catch(e){
						try{
							XMLHttpReq=new ActiveXObject("Microsoft.XMLHTTP");
						}catch(e){}
					}
				}
			}
			//发送请求函数
			function send(url){
				
				createXMLHttpRequest();
				XMLHttpReq.open("GET",url,true);
				XMLHttpReq.onreadystatechange=parse;   //指定响应的函数
				XMLHttpReq.send(null);  //发送请求
			}
			function parse(){
				
				if(XMLHttpReq.readyState==4){ //对象状态
					if(XMLHttpReq.status==200){//信息已成功返回，开始处理信息
						
						if(XMLHttpReq.responseText!=""){				
							var res = XMLHttpReq.responseXML.getElementsByTagName("content")[0].firstChild.data;		
							if (res == "ok") {
								document.getElementById("status").innerHTML="该用户名可以使用！";
							} else {
								document.getElementById("status").innerHTML="该用户名已经存在！";
								window.alert("该用户名已经存在！");
							}
						}
						
					
					}else{
						window.alert("所请求的页面有异常");
					}
				}
			}
			//--------------------Ajax end--------------------
			
			//身份验证 
			function checkUsername(){
				
				var username = document.getElementById("username").value;
				//alert(username);
				if(username == ""){
					alert("请输入用户名！");
					return false;
				} else{
					send('ajax.do?username='+username);
				}
			}
		</script>
	</head>

	<body bgcolor="#52BDFE">
		<table height="100%" width="100%">
			<tr>
				<td align="center">

					<table width="572" height="307" background="images/login.jpg">
						<tr>
							<td width="60%"></td>
							<td>
								<form name="form1" action="register.do" method="post"
									onsubmit="return validRegister(this);">
									<table width="250" border="0">
										<tr>
											<td colspan="2">
												<div id="status" style="color: #C10202;padding-top:10px">
													注册新用户
												</div>
											</td>
										</tr>
										<tr>
											<td>
												<span style="color: #C10202">*</span>用户名：
											</td>
											<td>
												<input type="text" name="username" size="10"
													onblur="checkUsername();" id="username">
											</td>
										</tr>
										<tr>
											<td>
												<span style="color: #C10202">*</span>密码：
											</td>
											<td>
												<input type="password" name="password1" size="10">
											</td>
										</tr>
										<tr>
											<td>
												<span style="color: #C10202">*</span>确认密码：
											</td>
											<td>
												<input type="password" name="password2" size="10">
											</td>
										</tr>
										<tr>
											<td>
												<span style="color: #C10202">*</span>姓名：
											</td>
											<td>
												<input type="text" name="name" size="10">
											</td>
										</tr>
												<input type="hidden" value="1" name="shenfen">
												
										<tr>
											<td>
												<span style="color: #C10202">*</span>性别：
											</td>
											<td>
												男
												<input type="radio" value="1" name="sex">
												女
												<input type="radio" value="0" name="sex">
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<a href="login.jsp">返回登录页</a>
												<input type="submit" name="submit" value="提交注册">
											</td>
										</tr>
									</table>
								</form>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

	</body>
</html>
