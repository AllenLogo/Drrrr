var ws = null;

function startWebSocket() {

	if ('WebSocket' in window) {
		try {
			ws = new WebSocket("ws://" + wsPath + "HallWebSocketServer");
		} catch (e) {
			alert("浏览器不支持");
		}
	} else if ('MozWebSocket' in window) {
		try {
			ws = new MozWebSocket("ws://" + wsPath + "HallWebSocketServer");
		} catch (e) {
			alert("浏览器不支持");
		}
	} else {
		alert("浏览器不支持");
	}
	ws.onmessage = function(evt) {
		analyzeMessage(evt.data);
	};
	ws.onclose = function(evt) {
	};
	ws.onerror = function(event) {
	};
	ws.onopen = function(evt) {
	};
}
/*
 * 信息处理函数
 * ------------
 *信息类型
 *1、所有聊天室信息
 *2、聊天室新建信息
 *3、聊天室关闭信息
 */
function analyzeMessage(msg) {
    var t = json_parse(msg);
    var type = t.type;
    if( type == "01" ){
    	for( var i in t.rooms ){
    		addRoom(t.rooms[i]);
    	}
    }else if (type == "02"){
    	addRoom(t.room);
    }else if(type == "03"){
    	updateRoom(t.room);
    }else if(type == "04"){
    	removeRoom(t.room);
    }
    type=null;delete type;
    t = null;delete t;
}

function addRoom(room){
	if( room["roompwd"] == "true" ){
		$("#roomlist2").prepend("<div id=\""+
				room["roomname"]+"\" ><a href='javascript:addroom(\""+
				room["roomname"]+"\");' >"+
				room["roomname"]+"</a>------"+
				room["roomhost"]+"|("+
				room["roomnumber"]+"/"+
				room["roomcount"]+")</div>");
	}else if( room["roompwd"] == "false" ){
		$("#roomlist1").prepend("<div id=\""+
				room["roomname"]+"\" ><a href='http://"+
				wsPath+"room/"+
				encodeURI(encodeURI(room["roomname"]))+"' >"+
				room["roomname"]+"</a>------"+
				room["roomhost"]+"|("+
				room["roomnumber"]+"/"+
				room["roomcount"]+")</div>");
	}
}

function updateRoom(room){
	if( room["roompwd"] == "true" ){
		$("#roomlist2").children("div[id='"+room["roomname"]+"']").html("<a href='javascript:addroom(\""+
				room["roomname"]+"\");' >"+
				room["roomname"]+"</a>------"+
				room["roomhost"]+"|("+
				room["roomnumber"]+"/"+
				room["roomcount"]+")");
	}else if( room["roompwd"] == "false" ){
		$("#roomlist1").children("div[id='"+room["roomname"]+"']").html("<a href='http://"+
				wsPath+"room/"+
				encodeURI(encodeURI(room["roomname"]))+"' >"+
				room["roomname"]+"</a>------"+
				room["roomhost"]+"|("+
				room["roomnumber"]+"/"+
				room["roomcount"]+")");
	}
}

function updateRoom(room){
	if( room["roompwd"] == "true" ){
		$("#roomlist2").children("div[id='"+room["roomname"]+"']").remove();
	}else if( room["roompwd"] == "false" ){
		$("#roomlist1").children("div[id='"+room["roomname"]+"']").remove();
	}
}