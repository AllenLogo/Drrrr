package hall.websocket.interceptor;

import java.util.Map;
import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;



public class HallHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

	@Override
	public boolean beforeHandshake(ServerHttpRequest request,ServerHttpResponse response, WebSocketHandler handler,Map<String,Object> map) throws Exception {
		
		ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
		HttpSession session = servletRequest.getServletRequest().getSession(false);
		
		if (request instanceof ServletServerHttpRequest) {
			
			String name = session.getAttribute("name") != null ? (String)session.getAttribute("name"):null;
	        String ip = session.getAttribute("ip") != null ? (String)session.getAttribute("ip"):null;
	        String state = session.getAttribute("state") != null ? (String)session.getAttribute("state"):null;
			
			if ( name != null && name != "" &&
		        	ip != null && ip != "" &&
		        	state == "1" ) {
				map.put("name", name);
				map.put("ip", ip);
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	@Override
	public void afterHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
	}
}
