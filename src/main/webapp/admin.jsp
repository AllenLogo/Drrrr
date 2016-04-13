<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Drrrr-admin</title>
	<link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="themes/icon.css">
	<style type="text/css">
	*{
		font-size:12px;
	}
	body {
	    font-family:verdana,helvetica,arial,sans-serif;
	    padding:20px;
	    font-size:12px;
	    margin:0;
	}
	h2 {
	    font-size:18px;
	    font-weight:bold;
	    margin:0;
	    margin-bottom:15px;
	}
	.demo-info{
		padding:0 0 12px 0;
	}
	.demo-tip{
		display:none;
	}
	</style>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout" style="height:100%;">
	<div data-options="region:'west',split:true,title:'菜单'" style="width:150px;">
		<div class="easyui-accordion" data-options="fit:true,border:false">
				<div title="大厅"  style="padding:10px;">
					<a href="#" title="大厅用户" onclick="addTab('大厅用户','<%=basePath %>admin_hall.jsp')" style="width: 100%;" class="easyui-linkbutton" data-options="plain:true">大厅用户</a>
				</div>
				<div title="聊天室" data-options="selected:true" style="padding:10px;">
					<a href="#" title="在线聊天室" onclick="addTab('在线聊天室','<%=basePath %>admin_room.jsp')"  style="width: 100%;" class="easyui-linkbutton" data-options="plain:true">在线聊天室</a>
				</div>
		</div>
	</div>
	<div data-options="region:'center',split:true,title:'Center',collapsible:false">
		<div id="tt" class="easyui-tabs">
		</div>
	</div>
	<div data-options="region:'south',split:true" style="height:10px;"></div>
<script type="text/javascript">
function addTab(subtitle, url) {

	if (!$('#tt').tabs('exists', subtitle)) {
			$('#tt').tabs('add', {
				title : subtitle,
				content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:570px;padding:0px;"></iframe>',
				closable : true
			});		

	} else {
		$('#tt').tabs('select', subtitle);
	}
		
}
</script>
</body>
</html>