package io.chagchagchag.example.foobar.websocket.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

@Slf4j
@Service
public class ChattingService {
  private static Map<String, Many<Chat>> chattingSinkMap = new ConcurrentHashMap<>();

  public Flux<Chat> register(String userName){
    Many<Chat> sink = Sinks.many().unicast().onBackpressureBuffer();
    chattingSinkMap.put(userName, sink);
    return sink.asFlux();
  }

  public boolean sendMessage(String userName, Chat chat){
    log.info("userName : {}, chat {}", userName, chat);

    if(!chattingSinkMap.containsKey(userName)) return false;

    chattingSinkMap
        .computeIfPresent(userName, (key, sink) ->{
          sink.tryEmitNext(chat);
          return sink;
        });

    return true;
  }
}
