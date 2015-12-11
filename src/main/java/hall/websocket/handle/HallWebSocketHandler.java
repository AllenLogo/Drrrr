package hall.websocket.handle;

import hall.entity.Hall;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import room.entity.Rooms;


public class HallWebSocketHandler implements WebSocketHandler {
 
	private Logger log = Logger.getLogger(HallWebSocketHandler.class);
    
    /**
     * websocket链接成功，websocketsession加入uer
     * */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	//获取用户的名称，IP信息
    	String name = (String) session.getAttributes().get("name");
    	String ip = (String) session.getAttributes().get("ip");
    	//分配大厅WebSocketSession
    	Hall.getInstance().insertHall(session);
    	log.info("[用户："+name+"，IP地址："+ip+"，事件：分配大厅WebSocketSession]");
    	//发送聊天室信息
    	try{
    		JSONObject json = JSONObject.fromObject("{}");
        	json.accumulate("type", 1);
        	json.accumulate("room", Rooms.getInstance().getHallRooms());
        	session.sendMessage(new TextMessage(json.toString().getBytes()));
    	} catch(Exception e){
    		e.printStackTrace();
    	}
    }
 
    
    /**
     * websocketsession发送消息
     * */
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    	//获取用户的名称，IP信息
    	String name = (String) session.getAttributes().get("name");
    	String ip = (String) session.getAttributes().get("ip");
    	//分配大厅WebSocketSession
    	Hall.getInstance().insertHall(session);
    	log.info("[用户："+name+"，IP地址："+ip+"，事件：企图用大厅WebSocketSession发送信息]");
    } 
    
    /**
     * webscpoketsession出错 关闭websocketsession
     * */    
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
        	session.close();
        	String name = (String) session.getAttributes().get("name");
        	String ip = (String) session.getAttributes().get("ip");
        	//分配大厅WebSocketSession
        	Hall.getInstance().insertHall(session);
        	log.info("[用户："+name+"，IP地址："+ip+"，事件：大厅WebSocketSession发送错误，关闭当前大厅WebSocketSession]");
        	Hall.getInstance().removeHall(session);
        }
    }
 
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
    	String name = (String) session.getAttributes().get("name");
    	String ip = (String) session.getAttributes().get("ip");
    	//分配大厅WebSocketSession
    	Hall.getInstance().insertHall(session);
    	log.info("[用户："+name+"，IP地址："+ip+"，事件：关闭大厅WebSocketSession]");
    	Hall.getInstance().removeHall(session);
    }
 
    public boolean supportsPartialMessages() {
        return false;
    }
 
}