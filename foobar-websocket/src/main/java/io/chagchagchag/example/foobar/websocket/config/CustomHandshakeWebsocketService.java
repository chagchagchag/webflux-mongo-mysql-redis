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

    return Mono.just(userName)
        .switchIfEmpty(Mono.defer(() -> { // 헤더가 없을 경우 세션 종료 처리
          exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
          exchange.getResponse().setComplete();
          return Mono.empty();
        }))
        .flatMap(userNameValue -> { // 헤더가 존재하면 'user-name' 이라는 새로운 헤더를 넣어준다.
          exchange.getAttributes().put("user-name", userNameValue);
          return super.handleRequest(exchange, handler);
        });
  }
}
