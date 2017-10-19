<%@ page language="java" pageEncoding="UTF-8"%>

<html>
	<head>

		<title>无线点餐后台管理系统 - 登录</title>

		<link rel="stylesheet" type="text/css" href="css/styles.css">
		<script language="javascript">
			function validate()
			{
				if(document.form1.username.value=="")
				{
					alert('请输入你的名字!');
					document.form1.username.focus();
					return false;
				}
				if(document.form1.password.value=="")
				{
					alert('你输入你的密码!');
					document.form1.password.focus();
					return false;
				}
				/*
				if(!checkShenfen()){
					alert('你输入你的身份!');
					//document.form1.shenfen.focus();
					return false;
				}
				*/
				return true;
			}
			/*
			function checkShenfen(){
				var shenfen = document.getElementsByName("shenfen");
				var count = 0;
				for (var i=0; i<shenfen.length; i++) {
					if (shenfen[i].checked) {
						count++;
						break;
					}
				}
					if (count == 0) {
						return false;
					}
					return true;
			}
			*/
		</script>
	</head>
	<body bgcolor="#52BDFE" onload=document.form1.username.focus();>

		<table height="100%" width="100%">
			<tr>
				<td align="center">
					<table width="572" height="307" background="images/login.jpg">
						<tr>
							<td width="60%"></td>
							<td>
								<form name="form1" action="login.do" method="post"
									onsubmit="return(validate());">
									<table>
										<tr>
											<td>
												用户名：
											</td>
											<td>
												<input type="text" name="username" size="11">
											</td>
										</tr>
										<tr>
											<td style="padding-top: 5px;">
												密&nbsp;&nbsp;码：
											</td>
											<td style="padding-top: 5px;">
												<input type="password" name="password" size="11">
											</td>
										</tr>
										
										<tr>
											<td colspan="2" style="padding-top: 5px;">
												<a href="register.jsp">注册新用户</a>
												<input type="submit" name="submit" value="登录系统">
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
