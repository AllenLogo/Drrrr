package room.websocket.handle;

import message.service.MessageService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import room.entity.Room;
import tools.JsonTool;
import user.User;

public class RoomWebSocketHandler implements WebSocketHandler {

	private Logger log = Logger.getLogger(RoomWebSocketHandler.class);
	
	@Autowired
	private MessageService messageService;

	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		User user = (User) session.getAttributes().get("user");
		Room room = user.getRoom();
		room.sendMessage(JsonTool.buildMessage_login("1", user.getName(),
				user.getStyle()));

		session.sendMessage(new TextMessage(room.getRoom_info().getBytes()));
		this.log.info(room.getRoom_info());

		room.insertMember(session);
	}

	public void handleMessage(WebSocketSession session,
			WebSocketMessage<?> message) throws Exception {
		User user = (User) session.getAttributes().get("user");
		Room room = user.getRoom();
		room.sendMessage(JsonTool.getMessage(new String[] { "type", "2",
				"name", user.getName(), "content",
				message.getPayload().toString(), "style", user.getStyle() }));
		messageService.saveMessage(user.getName(), room.getRoomName(), message.getPayload().toString());

	}

	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
	}

	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus closeStatus) throws Exception {
		User user = (User) session.getAttributes().get("user");
		Room room = user.getRoom();
		room.removeMember(session);
		room.sendMessage(JsonTool.buildMessage_login("0", user.getName(),
				user.getStyle()));
	}

	public boolean supportsPartialMessages() {
		return false;
	}
}