package hall.websocket.config;

import hall.websocket.handle.HallWebSocketHandler;
import hall.websocket.interceptor.HallHandshakeInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;




@Configuration
@EnableWebMvc
@EnableWebSocket
public class HallWebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer{

	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		 registry.addHandler(HallWebSocketHandler(),"/HallWebSocketServer")
		 .addInterceptors(new HallHandshakeInterceptor());
	}
	
	@Bean
	public WebSocketHandler HallWebSocketHandler() {
		// TODO Auto-generated method stub
		return new HallWebSocketHandler();
	}
}
