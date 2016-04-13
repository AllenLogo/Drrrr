package hall.websocket.handle;

import hall.entity.Hall;
import java.io.IOException;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import room.entity.Rooms;
import tools.JsonTool;
import user.User;

public class HallWebSocketHandler
  implements WebSocketHandler
{
  private Logger log = Logger.getLogger(HallWebSocketHandler.class);

  public void afterConnectionEstablished(WebSocketSession session)
  {
    Hall.getInstance().insertHall(session);
    try
    {
      session.sendMessage(new TextMessage(JsonTool.getMessage(new String[] { "type", "01", "rooms", Rooms.getInstance().getHallRooms() }).getBytes()));
    } catch (IOException e) {
      this.log.info(((User)session.getAttributes().get("user")).info() + "服务器向其发送信息异常:" + e.getMessage());
    }
  }

  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
    throws Exception
  {
    this.log.info(((User)session.getAttributes().get("user")).info() + "事件：企图用大厅WebSocketSession向服务器发送信息:" + message.getPayload().toString());
  }

  public void handleTransportError(WebSocketSession session, Throwable exception)
    throws Exception
  {
    this.log.info(((User)session.getAttributes().get("user")).info() + "事件：大厅WebSocketSession异常:" + exception.getMessage());
  }

  public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception
  {
    Hall.getInstance().removeHall(session);
  }

  public boolean supportsPartialMessages() {
    return false;
  }
}