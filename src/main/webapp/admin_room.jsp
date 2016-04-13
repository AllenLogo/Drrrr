<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Drrrr-admin-room</title>
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
	<!-- json解析 -->
	<script type="text/javascript" src="js/JSON-js-master/json_parse_state.js" ></script>
</head>
<body style="height:590xp;padding:0px;">
	<!-- table begin -->
	<table id="dg" style="height:590xp;" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				pageSize:10">
		<thead>
			<tr>
				<th data-options="field:'roomname',align:'center',halign:'center'" width="25%">聊天室名</th>
				<th data-options="field:'roomhost',align:'center',halign:'center'" width="25%">聊天室主人</th>
				<th data-options="field:'roompwd',align:'center',halign:'center'" width="15%">密码</th>
				<th data-options="field:'roomcount',align:'center',halign:'center'" width="10%">限制人数</th>
				<th data-options="field:'roomnumber',align:'center',halign:'center'" width="10%">实际人数</th>
				<th data-options="field:'manager',align:'center',halign:'center'" formatter="formatManager1" width="10%">管理</th>
			</tr>
		</thead>
	</table>
	<!-- table end -->
	
	<!-- Dialog begin -->
	<div id="dlg" class="easyui-dialog" title="" closed="true" style="height:450px;width:80%;max-width:800px;padding:0px" data-options="
			iconCls:'icon-save',
			onResize:function(){
				$(this).dialog('center');
			}">
		<!-- table begin -->
		<table id="dg1" style="height:590xp;" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				pageSize:10">
		<thead>
			<tr>
				<th data-options="field:'username',align:'center',halign:'center'" width="25%">名称</th>
				<th data-options="field:'ip',align:'center',halign:'center'" width="25%">IP</th>
				<th data-options="field:'roommember',align:'center',halign:'center'" width="20%">成员</th>
				<th data-options="field:'manager',align:'center',halign:'center'" formatter="formatManager2" width="20%">管理</th>
			</tr>
		</thead>
	</table>
	<!-- table end -->
	</div>
	<!-- Dialog end -->
	
	<script>
		
		function pagerFilter(data){
			if (typeof data.length == 'number' && typeof data.splice == 'function'){	// is array
				data = {
					total: data.length,
					rows: data
				};
			}
			var dg = $(this);
			var opts = dg.datagrid('options');
			var pager = dg.datagrid('getPager');
			pager.pagination({
				onSelectPage:function(pageNum, pageSize){
					opts.pageNumber = pageNum;
					opts.pageSize = pageSize;
					pager.pagination('refresh',{
						pageNumber:pageNum,
						pageSize:pageSize
					});
					dg.datagrid('loadData',data);
				}
			});
			if (!data.originalRows){
				data.originalRows = (data.rows);
			}
			var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
			var end = start + parseInt(opts.pageSize);
			data.rows = (data.originalRows.slice(start, end));
			return data;
		}
		
		$(function(){
			$.post("<%=basePath %>admin/rooms", null,function(data) {
			data = json_parse(data);
			$('#dg').datagrid({loadFilter:pagerFilter}).datagrid('loadData', data);});
		});
	</script>
<script type="text/javascript">
function openDialog(roomname){
$.post("<%=basePath %>admin/room", {"roomname" : roomname,},
	function(data) {
			data = json_parse(data);
			$('#dg1').datagrid({loadFilter:pagerFilter}).datagrid('loadData', data);
			$("#dlg").panel("move",{top:$(document).scrollTop() + ($(window).height()) * 0.1}); 
			$('#dlg').panel({title: roomname});
			$('#dlg').dialog('open');
			});
}
function managerMember(username){
	roomname= $('#dlg').panel('options').title;
	$.post("<%=basePath %>admin/roommanager", {'roomname' : roomname,'username':username},
	function(data) {
			data = json_parse(data);
			if( data.type == 'error' ){
				$.messager.alert('结果',data.content,'error');
			}else{
				$.messager.alert('结果',data.content,'info');
			}
	});
}
function formatManager1(val,row){
	return "<a href='#' title='查看' onclick='openDialog(\""+row['roomname']+"\")' style='width: 100%;' class='easyui-linkbutton' data-options='plain:true'>查看</a>";
}
function formatManager2(val,row){
	return "<a href='#' onclick='managerMember(\""+row['username']+"\")' style='width: 100%;' class='easyui-linkbutton' data-options='plain:true'>关闭</a>";
}
</script>
</body>
</html>