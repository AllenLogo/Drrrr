package room.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

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
		if( !member.contains(session) ){
			member.add(session);
			System.out.println("添加成功");
			return true;
		}else
			return false;
	}
	
	public boolean removeMember(WebSocketSession session){
		String name =  (String) session.getAttributes().get("name");
		String ip =  (String) session.getAttributes().get("ip");
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
					System.out.println(message);
					log.info("[Name:"+name+",IP:"+ip+",服务器发送消息成功："+message+"]");
				} catch (IOException e) {
					log.error("[Name:"+name+",IP:"+ip+",服务器发送消息失败："+message+"]");
				}
			}
		}
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
		return this.pwd != null?true:false;
	}
	
	public boolean getPwd(String pwd) {
		return this.pwd != null?(this.pwd.equals(pwd)?true:false):true;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
