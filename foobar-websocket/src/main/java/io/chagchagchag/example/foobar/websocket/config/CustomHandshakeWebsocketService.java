package io.chagchagchag.example.foobar.websocket.config;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CustomHandshakeWebsocketService extends HandshakeWebSocketService {

  @Override
  public Mono<Void> handleRequest(ServerWebExchange exchange, WebSocketHandler handler) {
    String userName = exchange
        .getRequest()
        .getHeaders()
        .getFirst("X-USER-NAME");

    return Optional.ofNullable(userName)
        .map(_userName -> {
          return exchange.getSession()
              .flatMap(webSession -> {
                webSession.getAttributes().put("user-name", _userName);
                return super.handleRequest(exchange, handler);
              });
        })
        .orElseGet(() -> {
          exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
          return exchange.getResponse().setComplete();
        });
  }
}
