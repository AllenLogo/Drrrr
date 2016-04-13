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
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistration;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class HallWebSocketConfig extends WebMvcConfigurerAdapter
  implements WebSocketConfigurer
{
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry)
  {
    registry.addHandler(HallWebSocketHandler(), new String[] { "/HallWebSocketServer" })
      .addInterceptors(new HandshakeInterceptor[] { 
      new HallHandshakeInterceptor() });
  }

  @Bean
  public WebSocketHandler HallWebSocketHandler()
  {
    return new HallWebSocketHandler();
  }
}