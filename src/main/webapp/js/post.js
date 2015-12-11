// 创建聊天室
function CreateRoom() {
	var submitData = {
		"roomname" : $("#roomName").val(),
	};
	$.post(basePath+'createroom', submitData, function(data) {
		alert(data);
		if( data != "error" ){
			closeWebSocket();
		}
	});
}