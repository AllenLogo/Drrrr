package websocket.dao;

import org.springframework.web.socket.WebSocketMessage;

public interface MessageDao {
	
	public String getMessage(WebSocketMessage<?> message);
}
