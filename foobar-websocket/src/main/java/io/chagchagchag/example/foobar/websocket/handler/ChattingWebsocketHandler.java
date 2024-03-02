package io.chagchagchag.example.foobar.websocket.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class ChattingWebsocketHandler implements WebSocketHandler {
  private final ChattingService chattingService;

  @Override
  public Mono<Void> handle(WebSocketSession session) {
    String userName = (String) session.getAttributes().get("user-name");
    Flux<Chat> chattingFlux = chattingService.register(userName);
    chattingService.sendMessage(userName, new Chat(userName + "님이 채팅방에 입장하셨습니다.", "(System)"));

    session
        .receive()
        .doOnNext(webSocketMessage -> {
          String payload = webSocketMessage.getPayloadAsText();

          String [] args = payload.split(":");
          String to = args[0].trim();
          String message = args[1].trim();

          final boolean result = chattingService.sendMessage(to, new Chat(message, userName));

          if(!result)
            chattingService.sendMessage(userName, new Chat("대화상대가 없어요", "(System)"));
        })
        .subscribe();

    return session.send(
        chattingFlux.map(chat -> session.textMessage(chat.from() + ":" + chat.message()))
    );
  }

}
