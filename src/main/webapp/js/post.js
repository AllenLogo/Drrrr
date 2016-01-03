// 创建聊天室
function CreateRoom(roomname,member,pwd,$el) {
	var submitData = {
		"roomname" : roomname,
		"member":member,
		"pwd":pwd,
	};
	$.post(basePath+'createroom', submitData, function(data) {
		data = json_parse(data);
		if( ("type" in data) && data.type == "success" ){
			$.tooltip('创建成功，2秒后进入聊天室',2000,true);
			setTimeout(function(){ 
				var url = "http://" + wsPath + "room/"+encodeURI(encodeURI(roomname));
				$el.hDialog('close',{box:'#HBox1'},url);
			},2000);
		}else if( ("type" in data) && data.type == "error" ){
			$.tooltip(data.content);
		}
		
	});
}

function AddRoom(roomname,pwd,$el) {
	var submitData = {
		"pwd":pwd,
	};
	$.post(basePath+'addroom/'+encodeURI(encodeURI(roomname)), submitData, function(data) {
		data = json_parse(data);
		if( ("type" in data) && data.type == "success" ){
			$.tooltip('密码正确，2秒后进入聊天室',2000,true);
			setTimeout(function(){ 
				var url = "http://" + wsPath + "room/"+encodeURI(encodeURI(roomname));
				$el.hDialog('close',{box:'#HBox1'},url);
			},2000);
		}else if( ("type" in data) && data.type == "error" ){
			$.tooltip(data.content);
		}
		
	});
}