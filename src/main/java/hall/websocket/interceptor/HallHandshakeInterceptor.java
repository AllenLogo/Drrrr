/**
 * 作者：李鹏飞
 * 时间：2016-2-26
 * 拦截器
 * 路径：/HallWebSocketServer
 * 作用：
 * 	检验请求是否可以申请大厅级别websocket
 */
package hall.websocket.interceptor;

import java.util.Map;
import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import user.User;



public class HallHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

	@Override
	public boolean beforeHandshake(ServerHttpRequest request,ServerHttpResponse response, WebSocketHandler handler,Map<String,Object> map) throws Exception {
		
		ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
		HttpSession session = servletRequest.getServletRequest().getSession(false);
		
		if (request instanceof ServletServerHttpRequest) {
			
			User user = (User) session.getAttribute("user");
			
			if( user != null && user.isLogin() ){
				map.put("user", user);
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
