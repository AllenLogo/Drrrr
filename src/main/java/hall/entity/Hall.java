package hall.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class Hall {
	private Logger log = Logger.getLogger(Hall.class);
	private static List<WebSocketSession> hallList = Collections.synchronizedList(new ArrayList<WebSocketSession>());
	
	static class UserHolder {	  
		 static Hall user = new Hall();  
	}
	
	public static Hall getInstance() {    
	        return UserHolder.user;  
	}  
	
	public List<WebSocketSession> getHall(){
		return hallList;
	}
	
	public boolean insertHall(WebSocketSession session){
		String name =  (String) session.getAttributes().get("name");
		String ip =  (String) session.getAttributes().get("ip");
		if( !hallList.contains(session) ){
			hallList.add(session);
			log.info("[Name:"+name+",IP:"+ip+",事件：添加Hall池成功]");
			return true;
		}else{
			log.info("[Name:"+name+",IP:"+ip+",事件：添加Hall池失败]");
			return false;
		}
	}
	
	public boolean removeHall(WebSocketSession session){
		String name =  (String) session.getAttributes().get("name");
		String ip =  (String) session.getAttributes().get("ip");
		if( hallList.contains(session) ){
			hallList.remove(session);
			log.info("[Name:"+name+",IP:"+ip+",事件：移除Hall池成功]");
			return true;
		}else{
			log.info("[Name:"+name+",IP:"+ip+",事件：移除Hall池失败]");
			return false;
		}
	}
	
	public boolean findHall(WebSocketSession session){
		if( hallList.contains(session) )
			return false;
		else
			return true;
	}
	
	public void sendMessage(String message){
		TextMessage Textmsg = new TextMessage(message.getBytes());
		String name = null;
		String ip = null;
		for( WebSocketSession session : hallList ){
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
}
