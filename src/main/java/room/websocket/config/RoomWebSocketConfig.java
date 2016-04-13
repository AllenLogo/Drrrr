package room.websocket.config;

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
import room.websocket.handle.RoomWebSocketHandler;
import room.websocket.interceptor.RoomHandshakeInterceptor;

@Configuration
@EnableWebMvc
@EnableWebSocket
public class RoomWebSocketConfig extends WebMvcConfigurerAdapter
  implements WebSocketConfigurer
{
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry)
  {
    registry.addHandler(RoomWebSocketHandler(), new String[] { "/RoomWebSocketServer" })
      .addInterceptors(new HandshakeInterceptor[] { 
      new RoomHandshakeInterceptor() });
  }

  @Bean
  public WebSocketHandler RoomWebSocketHandler()
  {
    return new RoomWebSocketHandler();
  }
}