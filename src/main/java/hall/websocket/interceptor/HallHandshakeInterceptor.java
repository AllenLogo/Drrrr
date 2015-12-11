package hall.websocket.interceptor;

import java.util.Map;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;



public class HallHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
	
	private Logger log  = Logger.getLogger(HallHandshakeInterceptor.class);

	@Override
	public boolean beforeHandshake(ServerHttpRequest request,ServerHttpResponse response, WebSocketHandler handler,Map<String,Object> map) throws Exception {
		
		ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
		ServletServerHttpResponse servletResponse = (ServletServerHttpResponse) response;
		HttpSession session = servletRequest.getServletRequest().getSession(false);
		
		if (request instanceof ServletServerHttpRequest) {
			if ( session!=null && session.getAttribute("name") != null && session.getAttribute("ip") != null ) {
				String name = (String) session.getAttribute("name");
				String ip = (String) session.getAttribute("ip");
				map.put("name", name);
				map.put("ip", ip);
				return true;
			}else{
				log.info("登陆验证失败！");
				return false;
			}
		}else{
			log.info("请求类型错误");
			return false;
		}
	}
	
	@Override
	public void afterHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
	}
}
