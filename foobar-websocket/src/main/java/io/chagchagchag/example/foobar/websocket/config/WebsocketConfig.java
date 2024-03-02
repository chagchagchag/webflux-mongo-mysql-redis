package io.chagchagchag.example.foobar.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

@Configuration
public class WebsocketConfig {

  @Bean
  public WebSocketHandlerAdapter webSocketHandlerAdapter(
      CustomHandshakeWebsocketService customHandshakeWebsocketService
  ){
    customHandshakeWebsocketService.setSessionAttributePredicate(s -> true);
    return new WebSocketHandlerAdapter(customHandshakeWebsocketService);
  }

}
