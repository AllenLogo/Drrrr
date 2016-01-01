package websocket.interceptor;

import java.util.Map;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;



public class MyHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
	
	private Logger log  = Logger.getLogger(MyHandshakeInterceptor.class);

	@Override
	public boolean beforeHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler handler,
			Map<String,Object> map) throws Exception {
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			HttpSession session = servletRequest.getServletRequest().getSession(false);
			
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
				log.info(name+"验证失败！不能建立websocket链接");
				return false;
			}
		}else{
			log.info("非法请求");
			return false;
		}
	}
	
	@Override
	public void afterHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
	}
}
