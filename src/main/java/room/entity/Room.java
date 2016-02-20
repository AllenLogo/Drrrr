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
	
	//聊天室名称
	private String roomName;
	//聊天室主人
	private String host;
	//聊天室人数
	private int count;
	//聊天室人数
	private String pwd=null;
	//聊天室成员
	private List<WebSocketSession> member = Collections.synchronizedList(new ArrayList<WebSocketSession>());
	
	public Room(){
	}
	
	public List<WebSocketSession> getMember(){
		return member;
	}
	
	public boolean insertMember(WebSocketSession session){
		String name =  (String) session.getAttributes().get("name") != null?(String) session.getAttributes().get("name"):null;
		String ip =  (String) session.getAttributes().get("ip") != null?(String) session.getAttributes().get("ip"):null;
		if( !member.contains(session) ){
			member.add(session);
			log.info("[Name:"+name+",IP:"+ip+",事件：加入链表成功]");
			return true;
		}else{
			log.info("[Name:"+name+",IP:"+ip+",事件：加入链表失败]");
			return false;
		}
	}
	
	public boolean removeMember(WebSocketSession session){
		String name =  (String) session.getAttributes().get("name") != null?(String) session.getAttributes().get("name"):null;
		String ip =  (String) session.getAttributes().get("ip") != null?(String) session.getAttributes().get("ip"):null;
		if( member.contains(session) ){
			member.remove(session);
			log.info("[Name:"+name+",IP:"+ip+",事件：移除链表成功]");
			return true;
		}else{
			log.info("[Name:"+name+",IP:"+ip+",事件：移除链表失败]");
			return false;
		}
	}
	
	public boolean findMember(WebSocketSession session){
		if( member.contains(session) )
			return false;
		else
			return true;
	}
	
	public boolean findMember(String name){
		for( WebSocketSession m : member ){
			if( m.getAttributes().get("name").equals(name) )
				return false;
				
		}
		return true;
	}
	
	public void sendMessage(String message){
		TextMessage Textmsg = new TextMessage(message.getBytes());
		String name = null;
		String ip = null;
		for( WebSocketSession session : member ){
			name =  (String) session.getAttributes().get("name");
			ip =  (String) session.getAttributes().get("ip");
			if( session.isOpen() ){
				try {
					session.sendMessage(Textmsg);
					log.info("[Name:"+name+",IP:"+ip+",服务器发送消息成功："+message+"]");
				} catch (IOException e) {
					log.error("[Name:"+name+",IP:"+ip+",服务器发送消息失败："+message+"]");
				}
			}
		}
	}

	public String getRoom_info(){
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
	
	public boolean isCount() {
		return count >= member.size()?true:false;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean getPwd() {
		return this.pwd != null?true:false;
	}
	
	public boolean getPwd(String pwd) {
		return this.pwd != null?(this.pwd.equals(pwd)?true:false):true;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
