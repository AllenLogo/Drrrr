package websocket.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import org.springframework.web.socket.WebSocketSession;

import websocket.dao.WebSocketSessionPoolDao;

public class WebSocketSessionPool implements WebSocketSessionPoolDao {

	private static List<WebSocketSession> list =  Collections.synchronizedList(new ArrayList<WebSocketSession>());
	
	static class WebSocketPools {	  
		 static WebSocketSessionPool pool = new WebSocketSessionPool();  
	}
	
	public static WebSocketSessionPool getInstance() {    
	        return WebSocketPools.pool;  
	}  
	
	public boolean addWebSocetSession(WebSocketSession session){
		return list.add(session);
	}
	
	public boolean removeWebSocketSession(WebSocketSession session){
		return list.remove(session);
	}
	
	public void show(){
		System.out.println(list.size());
		for(WebSocketSession ws : list){
//			if( ws.isOpen() && ws != null )
				System.out.println(ws.hashCode());
			
		}
		System.out.println("----------------");
	}

	public WebSocketSession getWebSocketSession(String name){
		for(WebSocketSession ws : list){
			if( name.equals(ws.getAttributes().get("name")) )
				return ws;
		}
		return null;
	}
	
	public List<WebSocketSession> getWebScoketSession(List<String> name){
		List<WebSocketSession> result = new ArrayList<WebSocketSession>();
		for(WebSocketSession ws : list){
			if( name.contains(ws.getAttributes().get("name")) )
				result.add(ws);
		}
		return result;
	}
}
