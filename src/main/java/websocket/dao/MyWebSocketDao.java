package websocket.dao;

import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public interface MyWebSocketDao {
	
	public void getMessage(WebSocketSession session,WebSocketMessage<?> message);
}
