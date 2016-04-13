/*
 * 时间：2016-02-19
 * 作者：李鹏飞
 * 功能：聊天室页面js特效
 */

/**
 * 聊天室初始化函数
 * 1、房间名
 * 2、房间实际人数
 * 3、房间预设人数
 * 4、用户列表
 */
var RoomNumber=0;
var RoomCount=0;
function init(room_inf){
	setRoom_name(room_inf.room_name);
	setRoom_number(room_inf.room_number, room_inf.room_count);
	setRoom_list(room_inf.room_list);
}

/**
 * 设置聊天室名称
 * @param room_name
 */
function setRoom_name(room_name){
	$("#room_name").children(".room-title-name").html(room_name);
}

/**
 * 设置聊天室人数
 * @param room_number
 */
function setRoom_number(room_number,room_count){
	RoomNumber = parseInt(room_number);
	RoomCount = parseInt(room_count);
	$("#room_name").children(".room-title-capacity").html("("+room_number+"/"+room_count+")");
}

function updateRoom_number(){
	RoomNumber=parseInt(RoomNumber+1);
	$("#room_name").children(".room-title-capacity").html("("+RoomNumber+"/"+RoomCount+")");
}
function updateRoom_number_(){
	RoomNumber=parseInt(RoomNumber-1);
	$("#room_name").children(".room-title-capacity").html("("+RoomNumber+"/"+RoomCount+")");
}

function updateRoom_number(){
	RoomNumber=RoomNumber+1;
	$("#room_name").children(".room-title-capacity").html("("+RoomNumber+"/"+RoomCount+")");
}

/**
 * 设置聊天室成员列表
 * @param room_list
 * {"host":{'name':'lpf','style':'zaika'},"number":{'name':'allen','style':'zaika'}}
 */
function setRoom_list(room_list){
	for(var o in room_list){
		setRoom_numbers(room_list[o]);
    }
}

/**
 * 设置聊天室成员列表——成员
 * @param room_number
 */
/* 
 * <li class="dropdown clearfix symbol-wrap-zaika-2x" title="拥抱世界的爱意❤">
 * 	<ul role="menu" class="dropdown-menu"></ul>
 * 	<div data-toggle="dropdown" class="name-wrap">
 * 		<span class="symbol symbol-zaika-2x"></span>
 * 		<span class="select-text name">拥抱世界的爱意❤</span>
 * 	</div>
 * </li>
 * */
function setRoom_numbers(room_number){
	$("#user_list").append("<li class='dropdown clearfix symbol-wrap-"+room_number.style+"' title='"+room_number.name+"'>"+
			"<ul role='menu' class='dropdown-menu'></ul>"+
			"<div data-toggle='dropdown' class='name-wrap'>"+
			"<span class='symbol symbol-"+room_number.style+"'></span>"+
			"<span class='select-text name'>"+room_number.name+"</span></div></li>");
}

function remvoeRoom_numbers(name){
	$("#user_list").children("li[title='"+name+"']").remove();
}

