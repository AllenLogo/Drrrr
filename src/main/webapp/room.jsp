<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String wsPath = request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
String name = (String) session.getAttribute("name");
 %>
<!doctype html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- begin 表情框 -->
<%-- <link href="<%=basePath %>css/bootstrap.min.css" rel="stylesheet"> --%>
<%-- <link rel="stylesheet" type="text/css" href="<%=basePath %>css/default.css"> --%>
<%-- <link href="<%=basePath %>css/cover.css" rel="stylesheet"> --%>
<link rel="stylesheet" href="<%=basePath %>css/font-awesome.min.css">
<link href="<%=basePath %>lib/css/nanoscroller.css" rel="stylesheet">
<link href="<%=basePath %>lib/css/emoji.css" rel="stylesheet">
<!-- end 表情框 -->

<!-- begin 对话框 -->
<link href="<%=basePath %>css/app.css?v=20160213.20.22" rel="stylesheet">
<!-- end 对话框 -->
</head>
<body onload="startWebSocket()">
	<div class="message_box select-none">
  		<div class="message_box_effect_wraper">
    		<div class="message_box_inner">
        		<div class="clearfix">
          			<h2 id="room_name" class="select-text">
            			<span class="room-title-capacity">(/)</span>
            			<span class="room-title-name"></span>
          			</h2>
          			<span class="to-whom">To</span>
        		</div>

		        <div class="room-input-wrap">
			        <p class="lead emoji-picker-container">
			        	<textarea id="textmsg" name="message" placeholder="Textarea" data-emojiable="true" class="form-control" tabindex="1" maxlength="140" ></textarea>
					</p>
		        </div>

		        <div class="room-submit-wrap">
		          <input class="form-control room-submit-btn" name="post" value="POST!" tabindex="3" type="button" onclick="getValue()" >
		        </div>

		      <div style="display: block;" id="setting_pannel" class="panel-hide is-host">
		        <ul id="user_list" class="user-list">
		        </ul>
		      </div>
    		</div>
  		</div>
	</div>
	<!-- begin talk show div -->
	<div id="talks" class="talks select-none">
			
	</div>
	<!-- end talk show div -->

	<script src="http://libs.useso.com/js/jquery/2.1.1/jquery.min.js" type="text/javascript"></script>
	<script>window.jQuery || document.write('<script src="<%=basePath %>js/jquery-2.1.1.min.js"><\/script>');</script>
	
	<!-- Begin emoji-picker JavaScript -->
	<script src="<%=basePath %>lib/js/nanoscroller.min.js"></script>
	<script src="<%=basePath %>lib/js/tether.min.js"></script>
	<script src="<%=basePath %>lib/js/config.js"></script>
	<script src="<%=basePath %>lib/js/util.js"></script>
	<script src="<%=basePath %>lib/js/jquery.emojiarea.js"></script>
	<script src="<%=basePath %>lib/js/emoji-picker.js"></script>
	<!-- End emoji-picker JavaScript -->

	<script type="text/javascript" src="<%=basePath %>js/JSON-js-master/json_parse_state.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/room-websocket.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/room.js" ></script>

	<script>
		var ws = null;
		var name = "<%=name %>";
		var wsPath = "<%=wsPath %>";
		var basePath = '<%=basePath %>';
		
	    $(function() {
	      window.emojiPicker = new EmojiPicker({
	        emojiable_selector: '[data-emojiable=true]',
	        assetsPath: '<%=basePath %>lib/img',
	        popupButtonClasses: 'fa fa-smile-o'
	      });
	      window.emojiPicker.discover();
	    });
  	</script>
</body>
</html>