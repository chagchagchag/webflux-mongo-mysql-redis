package io.chagchagchag.example.foobar.websocket.config;

import io.chagchagchag.example.foobar.websocket.handler.ChattingWebsocketHandler;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

@Configuration
public class MappingConfig {
  @Bean
  public SimpleUrlHandlerMapping simpleUrlHandlerMapping(
      ChattingWebsocketHandler chattingWebsocketHandler
  ){
    Map<String, WebSocketHandler> urlMapper = Map.of(
        "/chatting", chattingWebsocketHandler
    );

    SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
    mapping.setOrder(1);
    mapping.setUrlMap(urlMapper);
    return mapping;
  }
}
