/**
 * 作者：李鹏飞
 * 时间：2016-2-26
 * 大厅实体类
 */
package hall.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import tools.JsonTool;
import user.User;

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
		User user = (User) session.getAttributes().get("user");
		if( !hallList.contains(session) ){
			hallList.add(session);
			log.info("[Name:"+user.getName()+",IP:"+user.getIp()+",事件：添加Hall池成功]");
			return true;
		}else{
			log.info("[Name:"+user.getName()+",IP:"+user.getIp()+",事件：添加Hall池失败]");
			return false;
		}
	}
	
	public boolean removeHall(WebSocketSession session){
		User user = (User) session.getAttributes().get("user");
		if( hallList.contains(session) ){
			hallList.remove(session);
			log.info("[Name:"+user.getName()+",IP:"+user.getIp()+",事件：移除Hall池成功]");
			return true;
		}else{
			log.info("[Name:"+user.getName()+",IP:"+user.getIp()+",事件：移除Hall池失败]");
			return false;
		}
	}
	
	public boolean findHall(WebSocketSession session){
		if( hallList.contains(session) )
			return false;
		else
			return true;
	}
	
	public String getHallUser(){
		User user;
		JSONArray res_data = JSONArray.fromObject("[]");
		for(WebSocketSession session : hallList ){
				user = (User) session.getAttributes().get("user");
				res_data.add(JsonTool.getMessage("username",user.getName(),"ip",user.getIp()));
		}
		return res_data.toString();
	}
	
	public void sendMessage(String message){
		TextMessage Textmsg = new TextMessage(message.getBytes());
		User user;
		for( WebSocketSession session : hallList ){
			user = (User) session.getAttributes().get("user");
			if( session.isOpen() ){
				try {
					session.sendMessage(Textmsg);
					log.info("[Name:"+user.getName()+",IP:"+user.getIp()+",服务器发送消息成功："+message+"]");
				} catch (IOException e) {
					log.error("[Name:"+user.getName()+",IP:"+user.getIp()+",服务器发送消息失败："+message+"]");
				}
			}
		}
	}
}
