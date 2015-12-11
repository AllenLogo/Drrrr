package websocket.impl;

import org.springframework.web.socket.WebSocketMessage;

import websocket.dao.MessageDao;

public class MessageImpl implements MessageDao {

	public String getMessage(WebSocketMessage<?> message) {
		String coutent =message.getPayload().toString();
		return coutent;
	}

}
