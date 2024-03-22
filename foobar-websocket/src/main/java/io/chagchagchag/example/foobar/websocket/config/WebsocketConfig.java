package io.chagchagchag.example.foobar.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

@Configuration
public class WebsocketConfig {

  @Bean
  public WebSocketHandlerAdapter webSocketHandlerAdapter(
      HandshakeWebSocketService customHandshakeWebsocketService
  ){
    // websocket 에서 attribute 를 바이패스
    customHandshakeWebsocketService.setSessionAttributePredicate(s -> true);
    return new WebSocketHandlerAdapter(customHandshakeWebsocketService);
  }

}
