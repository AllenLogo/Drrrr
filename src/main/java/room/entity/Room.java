package room.entity;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import tools.JsonTool;

public class Room {
	private Logger log = Logger.getLogger(Room.class);
	
	private String roomName;				//聊天室名称
	private String host;					//聊天室主人
	private int count;						//聊天室限制人数
	private int number;					//聊天室实际人数
	private String pwd;						//聊天室密码
	private List<WebSocketSession> member;	//聊天室成员链表
	private boolean roomState;				//聊天室状态标志
	
	/**
	 * 构造函数
	 */
	public Room(String roomName,String host,int count,String pwd){
		setRoomName(roomName);
		setHost(host);
		setCount(count);
		setPwd(pwd);
		member = Collections.synchronizedList(new ArrayList<WebSocketSession>());
		OpenRoom();
	}
	
	/**
	 * 
	 * @param session
	 * @return
	 * 
	 * 用户加入
	 * 人数增加
	 * 日志记录
	 * 
	 */
	public boolean insertMember(WebSocketSession session){
		if( !this.isOpen() ){return false;}
		if(this.number >= count) {return false;}
		
		String name =  (String) session.getAttributes().get("name") != null?(String) session.getAttributes().get("name"):null;
		String ip =  (String) session.getAttributes().get("ip") != null?(String) session.getAttributes().get("ip"):null;
		if( session.isOpen() && !member.contains(session)  ){
			this.member.add(session);
			this.number++;
			log.info("[Name:"+name+",IP:"+ip+",事件：加入聊天室"+roomName+"成功]");
			return true;
		}else{
			log.info("[Name:"+name+",IP:"+ip+",事件：加入聊天室"+roomName+"成功]");
			return false;
		}
	}
	
	/**
	 * 
	 * @param session
	 * @return
	 * 
	 * 用户退出
	 * 人数减少
	 * 主人销毁
	 * 日志记录
	 */
	public boolean removeMember(WebSocketSession session){
		if( !this.isOpen() ){return false;}
		if(this.number == 0) {return false;}
		
		String name =  (String) session.getAttributes().get("name") != null?(String) session.getAttributes().get("name"):null;
		String ip =  (String) session.getAttributes().get("ip") != null?(String) session.getAttributes().get("ip"):null;
		if( this.member.contains(session) ){
			this.member.remove(session);
			this.number--;
			if (this.number == 0){this.dectoryRoom();}
			log.info("[Name:"+name+",IP:"+ip+",事件：移除聊天室"+roomName+"成功]");
			return true;
		}else{
			log.info("[Name:"+name+",IP:"+ip+",事件：移除聊天室"+roomName+"成功]");
			return false;
		}
	}
	
	
	/**
	 * 
	 * @param name
	 * @return
	 * 
	 * 用户查找
	 * 
	 */
	public boolean findMember(String name){
		if( !this.isOpen() ){return false;}
		for( WebSocketSession m : member ){
			if( m.getAttributes().get("name").equals(name) )
				return true;
				
		}
		return false;
	}
	
	public void sendMessage(String message){
		if( !this.isOpen() ){return ;}
		TextMessage Textmsg = new TextMessage(message.getBytes());
		for( WebSocketSession session : member ){
			if( session.isOpen() ){
				try {
					session.sendMessage(Textmsg);
					log.info("[Name:"+session.getAttributes().get("name")+",IP:"+session.getAttributes().get("ip")+",服务器发送消息成功："+message+"]");
				} catch (IOException e) {
					log.error("[Name:"+session.getAttributes().get("name")+",IP:"+session.getAttributes().get("ip")+",服务器发送消息失败："+message+"]");
				}
			}
		}
	}

	public String getRoom_info(){
		if( !this.isOpen() ){return "";}
		Map<String,String> map = new HashMap<String,String>();
		map.put("room_name", this.roomName);
		map.put("room_number", ""+this.member.size());
		map.put("room_count", ""+this.count);
		map.put("room_list", getMember_map());
		
		String content = JsonTool.buildStrig(map);
		map.clear();
		map.put("type", "3");
		map.put("content", content);
		return JsonTool.buildStrig(map);
	}
	
	//{"host":{'name':'lpf','style':'zaika'},"number":{'name':'allen','style':'zaika'}}
	private String getMember_map(){
		if( !this.isOpen() ){return "";}
		Map<String,String> maps = new HashMap<String,String>();
		Map<String,String> map = new HashMap<String,String>();
		String name = "";
		String style = "";
		for( WebSocketSession session : member ){
			name = (String) session.getAttributes().get("name");
			style = (String) session.getAttributes().get("style");
			map.put("name", name);
			map.put("style", style);
			maps.put(name, JsonTool.buildStrig(map));
			map.clear();
		}
		return JsonTool.buildStrig(maps);
	}
	
	/**
	 * @return 新增聊天室，大厅更新聊天室列表所需信息
	 */
	public String getHall_Room_info(){
		if( !this.isOpen() ){return "";}
		return JsonTool.buildMessage_hall_02(roomName, host, this.member.size(), count, getPwd());
	}
	
	/**
	 * 聊天室销毁
	 * 内部调用
	 * @param name
	 */
	private void dectoryRoom(){
		if( !this.isOpen() ){return ;}
		this.member.clear();
		this.CloseRoom();
		Rooms.getInstance().removeRooms(this);
	}
	
	/**
	 * 聊天室销毁
	 * 主人外部调用
	 * @param name
	 */
	public void dectoryRoom(String name){
		if( !this.isOpen() ){return ;}
		if( !this.host.equals(name)){return ;}
		this.dectoryRoom();
	}
	
	
	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean getPwd() {
		return (this.pwd != null && this.pwd != "")?true:false;
	}
	
	public boolean checkPwd(String pwd) {
		return this.pwd.equals(pwd);
	}

	private void setPwd(String pwd) {
		if(pwd == null || "".equals(pwd)){
			this.pwd = "";
		}else{
			this.pwd = pwd;
		}
	}

	public int getNumber() {
		return number;
	}
	
	public boolean isCount() {
		return count >= number?true:false;
	}

	private void CloseRoom() {
		this.roomState = false;
	}

	private void OpenRoom() {
		this.roomState = true;
	}
	
	public boolean isOpen(){
		return this.roomState;
	}
}
