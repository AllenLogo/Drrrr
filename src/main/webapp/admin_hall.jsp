<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Drrrr-admin-hall</title>
	<link rel="stylesheet" type="text/css" href="<%=basePath %>themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath %>themes/icon.css">
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
	<script type="text/javascript" src="<%=basePath %>js/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/jquery.easyui.min.js"></script>
	<!-- json解析 -->
	<script type="text/javascript" src="<%=basePath %>js/JSON-js-master/json_parse_state.js" ></script>
</head>
<body style="height:590xp;padding:0px;">
	<table id="dg" style="height:590xp;" data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				pageSize:10">
		<thead>
			<tr>
				<th data-options="field:'username',align:'center',halign:'center'" width="44%">用户名</th>
				<th data-options="field:'ip',align:'center',halign:'center'" width="44%">IP地址</th>
			</tr>
		</thead>
	</table>
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
		function getData(){
			$.post("<%=basePath %>/admin/hall", null,
			function(data) {
				data = json_parse(data);
				$('#dg').datagrid({loadFilter:pagerFilter}).datagrid('loadData', data);});
		}
		$(function(){ getData(); });
	</script>
</body>
</html>