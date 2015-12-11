/*
 * 申请WebSocket
 * 大厅级别
 * 只接收信息
 * 信息包括：聊天室列表、聊天室新建、聊天室关闭，管理员通知。
 */
function startWebSocket() {

	if ('WebSocket' in window) {
		try {
			ws = new WebSocket("ws://" + wsPath + "HallWebSocketServer");
		} catch (e) {
			alert("链接方式1错误");
		}
	} else if ('MozWebSocket' in window) {
		try {
			ws = new MozWebSocket("ws://" + wsPath + "HallWebSocketServer");
		} catch (e) {
			alert("链接方式2错误");
		}
	} else {
		alert("无法链接");
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
    if( type == 1 ){
    	roomList = t.room;
    	for( var i in roomList ){
    		var div = document.createElement("div");
    		div.innerHTML = roomList[i]["roomname"]+"------"+roomList[i]["host"];
    		document.body.appendChild(div);
    		div = null;delete div;
        }
    }else if (type == 2){
    	var room = t.room;
    	roomList.push(room[0]);
    	var div = document.createElement("div");
		div.innerHTML = room[0]["roomname"]+"------"+room[0]["host"];
		document.body.appendChild(div);
		div = null;delete div;
		room = null;delete room;
    }else if( type == 3 ){
    	//聊天室关闭时，删除大厅显示信息
    }
    type=null;delete type;
    t = null;delete t;
}