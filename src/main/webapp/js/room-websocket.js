function getValue(){
	textmsg = $("div[placeholder=Textarea]").html();
	if ( textmsg != null && textmsg != "" )
		ws.send(textmsg);
	$("div[placeholder=Textarea]").html("");
}
/*
 * 申请WebSocket
 * 聊天室级别
 * 接收信息，发送聊天内容
 */
function startWebSocket() {
	
	if ('WebSocket' in window) {
		try {
			ws = new WebSocket("ws://" + wsPath + "RoomWebSocketServer");
		} catch (e) {
			alert("浏览器不支持无法链接");
		}
	} else if ('MozWebSocket' in window) {
		try {
			ws = new MozWebSocket("ws://" + wsPath + "RoomWebSocketServer");
		} catch (e) {
			alert("浏览器不支持无法链接");
		}
	} else {
		alert("浏览器不支持无法链接");
	}
	
	/*
	 * 接收信息响应
	 */
	ws.onmessage = function(evt) {
		/*
		 * 处理信息
		 */
		say(evt.data);
	};

	/*
	 * 关闭连接响应
	 */
	ws.onclose = function(evt) {
	};

	/*
	 * 链接错误响应
	 */
	ws.onerror = function(event) {
	};

	/*
	 * 链接打开响应
	 */
	ws.onopen = function(evt) {
	};
	
}
/*
 * 信息处理函数
 * 1、分类
 * 2、更新
 * ------------
 *信息分类
 *0、其他消息
 *1、所有聊天室信息
 *2、聊天室新建信息
 *3、聊天室关闭信息
 *信息格式：
 *{"type":消息类型1、2、3，"room":[{"聊天室名1","聊天室主人名1"},...]}
 *{"type":1,"room":[{"roomname":"asd","host":"ssaa"}]}
 */
function say(msg) {
    var t = json_parse(msg);
    var type = t.type;
    if ( type == 0){
    	message_logged_out(t.content.name);
    	remvoeRoom_numbers(t.content.name);
    	updateRoom_number_();
    }else if( type == 1 ){
    	message_logged_in(t.content.name);
    	setRoom_numbers(t.content);
    	updateRoom_number();
    }else if (type == 2){
    	message_receive(t.name,t.content,t.style);
    }else if( type == 3 ){
    	init(t.content);
    }else if (type == 4){
    	alert(t.content);
    	location.href = basePath+"hall.jsp";
    }
    type=null;delete type;
    t = null;delete t;
}

function message_logged_in(name){
	$("#talks").prepend("<div class='talk system join'>►► @"+name+" logged in. </div>");
}

function message_logged_out(name){
	$("#talks").prepend("<div class='talk system join'>►► @"+name+" logged out. </div>");
}

function message_receive(name,content,style){
	$("#talks").prepend("<dl class='talk "+style+" '>"+
			"<dt class='dropdown'>"+
				"<div class='avatar avatar-"+style+"'>"+
					"<span class='select-text'>"+name+"</span>"+
				"</div>"+
				"<div data-toggle='dropdown' class='name'>"+
					"<span class='select-text'>"+name+"</span>"+
				"</div>"+
				"<ul role='menu' class='dropdown-menu'></ul>"+
			"</dt>"+
			"<dd class='bounce'>"+
				"<div class='bubble'>"+
					"<div style='background-size: 65px auto;' class='tail-wrap center'>"+
						"<div class='tail-mask'></div>"+
					"</div>"+
					"<p class='body select-text'>"+content+"</p>"+
				"</div>"+
			"</dd>"+
		"</dl>");
}


