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
<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
<title>无标题文档</title>
<link rel="stylesheet" href="css/common.css"/><!-- 基本样式 -->

<style>
div {
	margin-left: auto;
	margin-right: auto;
}
</style>
</head>
<body onload="startWebSocket()">
	<div>WebSocket聊天室大厅
	<a href="javascript:;" class="demo6">创建聊天室</a>
	</div>
	<div id="HBox1">
				<ul class="list">
					<li>
						<strong>聊天室名称  <font color="#ff0000">*</font></strong>
						<div class="fl"><input type="text" name="roomname" value="" class="ipt roomname" /></div>
					</li>
					<li>
						<strong>聊天室人数 <font color="#ff0000">*</font></strong>
						<div class="fl"><input type="text" name="member" value="" class="ipt member" /></div>
					</li>
					<li>
						<strong>聊天室密码<font color="#ff0000">&nbsp;</font></strong>
						<div class="fl"><input size="6" type="password" name="pwd1" value="" class="ipt pwd1" /></div>
					</li>
					<li>
						<strong>确认密码<font color="#ff0000">&nbsp;</font></strong>
						<div class="fl"><input size="6" type="password" name="pwd2" value="" class="ipt pwd2" /></div>
					</li>
					<li><input type="submit" id="submitBtn1" value="确认提交" class="submitBtn" /></li>
				</ul>
		</div><!-- HBox end -->
		
		<div id="HBox">
				<ul class="list">
					<li>
						<strong>聊天室密码<font color="#ff0000">*</font></strong>
						<div class="fl"><input size="6" type="password" id="pwd" name="pwd" value="" class="ipt pwd" /></div>
					</li>
					<li><input type="submit" id="submitBtn" value="确认提交" class="submitBtn" /></li>
				</ul>
		</div><!-- HBox1 end -->
	<div style="border:1px solid #09F"></div>
	
	<div style="border:1px solid #09F"></div>
	聊天室列表
	<div id="roomlist" ></div>

<script type="text/javascript" src="js/JSON-js-master/json_parse_state.js" ></script>
<script type="text/javascript" src="js/jquery-1.11.1.min.js" ></script>
<script src="js/jquery.hDialog.min.js"></script>
<script type="text/javascript" src="js/websocket.js"></script>
<script type="text/javascript">
	var ws = null;
	var name = "<%=name %>";
	var wsPath = "<%=wsPath %>";
	var basePath = '<%=basePath %>';
	var roomList = new Array();

	var $el = $('.dialog');
	$('.demo6').hDialog({box:'#HBox1',width:600,height: 300,modalHide: false});
	
	//提交并验证表单
	$('#submitBtn1').click(function() {
		var Number = /^[0-9]+$/ ; //手机正则
		var $roomname = $('.roomname');
		var $member = $('.member'); 
		var $pwd1 = $('.pwd1');
		var $pwd2 = $('.pwd2');
		if($roomname.val() == ''){
			$.tooltip('聊天室名称不能为空'); $roomname.focus();
		}else if($member.val() == ''){
			$.tooltip('聊天室人数不能为空'); $member.focus();
		}else if(!Number.test($member.val())){
			$.tooltip('请输入整数'); $member.focus();
		}else if($pwd1.val() != $pwd2.val() ){
			$.tooltip('密码不正确'); $pwd2.focus();
		}else{
			CreateRoom($roomname.val(),$member.val(),$pwd1.val(),$el);
		}
	});
	$('#submitBtn').click(function() { 
		var $pwd = $('#pwd');
		if($pwd.val() == "" ){
			$.tooltip('密码不能为空'); $pwd.focus();
		}else{
			AddRoom($("#submitBtn").attr("name"),$pwd.val(),$el);
		}
	});
	function addroom(roomname){
		$("#submitBtn").attr("name",roomname);
		$('#'+roomname).hDialog({box:'#HBox',width:600,height: 150,modalHide: false});
		$('#'+roomname).trigger("click");
	}
	
</script>	
<script type="text/javascript" src="js/post.js"></script>
</body>
</html>