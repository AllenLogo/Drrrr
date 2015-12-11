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
	
	private String roomName;
	private String host;
	private static List<WebSocketSession> member = Collections.synchronizedList(new ArrayList<WebSocketSession>());
	
	static class RoomHolder {	  
		 static Room user = new Room();  
	}
	
	public static Room getInstance() {    
	        return RoomHolder.user;  
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
			log.info("[Name:"+name+",IP:"+ip+",事件：关闭WebSocket成功]");
			return true;
		}else{
			log.info("[Name:"+name+",IP:"+ip+",事件：关闭WebSocket失败]");
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
}
