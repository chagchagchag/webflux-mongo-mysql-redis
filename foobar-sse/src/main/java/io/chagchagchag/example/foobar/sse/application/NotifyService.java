package io.chagchagchag.example.foobar.sse.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotifyService {
  private static Sinks.Many<String> sink = Sinks.many().unicast().onBackpressureBuffer();

  public Flux<String> getMessageFromSink(){
    return sink.asFlux();
  }

  public void tryEmitNext(String message){
    log.info("message : {}", message);
    sink.tryEmitNext(message);
  }

}
