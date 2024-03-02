package io.chagchagchag.example.foobar.sse.application;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/notify")
@RestController
public class NotifyController {
  private static AtomicInteger lastEventId = new AtomicInteger(1);
  private final NotifyService notifyService;

  @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<ServerSentEvent<String>> getNotifications(){
    return notifyService
        .getMessageFromSink()
        .map(message -> {
          String id = lastEventId.getAndIncrement() + "";
          return ServerSentEvent
              .<String>builder()
              .event("notification")
              .id(id)
              .comment("알림메시지 에요.")
              .build();
        });
  }

  @PostMapping
  public Mono<String> addNotification(@RequestBody Event event){
    String message = String.format("%s:%s", event.type(), event.message());
    notifyService.tryEmitNext(message);
    return Mono.just("ok");
  }

}
