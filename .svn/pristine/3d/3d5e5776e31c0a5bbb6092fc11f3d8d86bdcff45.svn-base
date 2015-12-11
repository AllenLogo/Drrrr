/**
 *WebSocketSessionPool管理接口
 *WebScoketSession增加、删除、查找
 */
package websocket.dao;

import java.util.List;

import org.springframework.web.socket.WebSocketSession;

public interface WebSocketSessionPoolDao {

	public boolean addWebSocetSession(WebSocketSession session);
	public boolean removeWebSocketSession(WebSocketSession session);
	public WebSocketSession getWebSocketSession(String name);
	public List<WebSocketSession> getWebScoketSession(List<String> name);
	
	public void show();
	
}
