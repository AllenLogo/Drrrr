package websocket.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import websocket.dao.MessageDao;
import websocket.dao.MyWebSocketDao;
import websocket.dao.WebSocketSessionPoolDao;

public class MyWebSocketPool implements MyWebSocketDao {

	@Autowired
	private WebSocketSessionPoolDao wsPool;
	@Autowired
	private MessageDao msgDao;
	
	public void getMessage(WebSocketSession session, WebSocketMessage<?> message) {
		msgDao.getMessage(message);
		wsPool.getWebSocketSession("ss");
	}

}
