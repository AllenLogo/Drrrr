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

import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import room.entity.Rooms;
import tools.JsonTool;
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
    	try {
			session.sendMessage(new TextMessage(JsonTool.getMessage("type", "01","rooms",Rooms.getInstance().getHallRooms()).getBytes()));
		} catch (IOException e) {
			log.info(((User) session.getAttributes().get("user")).info()+"服务器向其发送信息异常:"+e.getMessage());
		}
    }
    
    /**
     * websocketsession发送消息
     * */
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    	//大厅级别websocket发送信息
    	log.info(((User) session.getAttributes().get("user")).info()+"事件：企图用大厅WebSocketSession向服务器发送信息:"+message.getPayload().toString());
    } 
    
    /**
     * webscpoketsession出错 关闭websocketsession
     * */    
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info(((User) session.getAttributes().get("user")).info()+"事件：大厅WebSocketSession异常:"+exception.getMessage());

    }
 
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
    	Hall.getInstance().removeHall(session);
    }
 
    public boolean supportsPartialMessages() {
        return false;
    }
 
}