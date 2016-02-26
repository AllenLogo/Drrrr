/**
 * 作者：李鹏飞
 * 时间：2016-2-26
 * 大厅级别websocket处理
 * 作用：
 * 聊天室信息转发
 */
package hall.websocket.handle;

import java.io.IOException;

import hall.entity.Hall;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import room.entity.Rooms;
import user.User;


public class HallWebSocketHandler implements WebSocketHandler {
 
	private Logger log = Logger.getLogger(HallWebSocketHandler.class);
    
    /**
     * websocket链接成功，websocketsession加入uer
     * */
    public void afterConnectionEstablished(WebSocketSession session){
    	
    	//分配大厅WebSocketSession
    	Hall.getInstance().insertHall(session);
    	
    	//发送聊天室信息
    	JSONObject json = JSONObject.fromObject("{}");
    	json.accumulate("type", "01");
    	json.accumulate("rooms", Rooms.getInstance().getHallRooms());
    	try {
			session.sendMessage(new TextMessage(json.toString().getBytes()));
			log.info("[服务器发送信息:"+json.toString()+"]");
		} catch (IOException e) {
			User user = (User) session.getAttributes().get("user");
			log.info("[用户："+user.getName()+"，IP地址："+user.getIp()+"，服务器向其发送信息异常:"+e.getMessage()+"]");
		}
    }
    
    /**
     * websocketsession发送消息
     * */
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    	//大厅级别websocket发送信息
    	User user = (User) session.getAttributes().get("user");
    	log.info("[用户："+user.getName()+"，IP地址："+user.getIp()+"，事件：企图用大厅WebSocketSession向服务器发送信息<BEGIN>"+message.getPayload().toString()+"<END>]");
    } 
    
    /**
     * webscpoketsession出错 关闭websocketsession
     * */    
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    	User user = (User) session.getAttributes().get("user");
        log.info("[用户："+user.getName()+"，IP地址："+user.getIp()+"，事件：大厅WebSocketSession异常<BERGIN>"+exception.getMessage()+"<END>]");

    }
 
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
    	Hall.getInstance().removeHall(session);
    }
 
    public boolean supportsPartialMessages() {
        return false;
    }
 
}