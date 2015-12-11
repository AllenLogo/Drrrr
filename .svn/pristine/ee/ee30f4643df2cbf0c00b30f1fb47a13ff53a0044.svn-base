<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>Drrr</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
		function Verification_Null(){
			
			return true;
		}
	</script>
</head>

<body>

	<center>
	<div id="login_error" >
		<%
		if( session.getAttribute("login_error") != null  ){%>
		<%=session.getAttribute("login_error") %>
		<%
		session.removeAttribute("login_error");
		} 
		%>
	</div>
		<form action="login" method="post" onsubmit="return Verification_Null();" >
			<table>
				<tr>
					<td>用户</td>
					<td><input type="text" name="name" /></td>
				</tr>
				<tr>
					<td colspan="2">
						<button type="submit">Login</button>
					</td>
				</tr>
			</table>
		</form>
	</center>
</body>
</html>
