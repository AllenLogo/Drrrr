<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
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
</head>

<body>
	<div style="position: absolute;top: 10%;left: 35%;right: 35%;width: 30%;" >
		<form action="login" method="post" onsubmit="return Verification_Null();" >
			<table>
				<tr>
					<td>用户名</td>
					<td><input id="username" type="text" name="name" /></td>
				</tr>
				<tr>
					<td colspan="2">
						<button type="submit">Login</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
<script src="<%=basePath%>js/jquery.min.js"></script>
<script src="<%=basePath%>js/jquery.hDialog.min.js"></script>
<script type="text/javascript">
	function Verification_Null(){
		username = $("#username").val();
		if (username == ""){
			$.tooltip('用户名不能为空！');
			return false;
		}else{
			return true;
		}
	}
</script>
</body>
</html>
