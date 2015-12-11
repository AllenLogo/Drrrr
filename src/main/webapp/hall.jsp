<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String wsPath = request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
String name = (String) session.getAttribute("name");
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<script type="text/javascript" src="js/JSON-js-master/json_parse_state.js" ></script>
<script type="text/javascript">
	var ws = null;
	var name = "<%=name %>";
	var wsPath = "<%=wsPath %>";
	var basePath = '<%=basePath %>';
	var roomList = new Array();
</script>
<script type="text/javascript" src="js/jquery-1.11.1.min.js" ></script>
<script type="text/javascript" src="js/websocket.js"></script>
<style>
div {
	margin-left: auto;
	margin-right: auto;
}
</style>
</head>
<body onload="startWebSocket()">
	<div id="room_create_error" >
		<%
		if( session.getAttribute("room_create_error") != null  ){%>
		<%=session.getAttribute("room_create_error") %>
		<%
		session.removeAttribute("room_create_error");
		} %>
	</div>
	<div>WebSocket聊天室大厅
		<form action="createroom" method="post">
			<input type="text" name="roomname" id="roomName" />
			<input type="submit" value="createRoom" />
		</form>
	</div>
	<div style="border:1px solid #09F"></div>
	
	<div style="border:1px solid #09F"></div>
	聊天室列表
</body>
</html>