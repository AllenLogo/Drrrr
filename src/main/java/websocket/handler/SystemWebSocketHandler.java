package websocket.handler;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import websocket.dao.MyWebSocketDao;
import websocket.dao.WebSocketSessionPoolDao;


public class SystemWebSocketHandler implements WebSocketHandler {
 
	private Logger log = Logger.getLogger(SystemWebSocketHandler.class);
	
	@Autowired
	private WebSocketSessionPoolDao wsPool;
	@Autowired
	private MyWebSocketDao mwsDao;
    
    /**
     * websocket链接成功，websocketsession加入uer
     * */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	String name = (String) session.getAttributes().get("name");
    	String ip = (String) session.getAttributes().get("ip");
    	System.out.println(wsPool.addWebSocetSession(session));
    	System.out.println(session.hashCode());
    	wsPool.show();
    	log.info("[用户："+name+"，IP地址："+ip+"，count：分配websocket]");
    }
 
    
    /**
     * websocketsession发送消息
     * */
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    	mwsDao.getMessage(session, message);
    } 
    
    /**
     * webscpoketsession出错 关闭websocketsession
     * */    
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
        	wsPool.removeWebSocketSession(session);
            session.close();
        }
    }
 
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
    	wsPool.removeWebSocketSession(session);
    	System.out.println(session.hashCode());
    	System.out.println("afterConnectionClosed:"+closeStatus);
    	wsPool.show();
    }
 
    public boolean supportsPartialMessages() {
        return false;
    }
 
}