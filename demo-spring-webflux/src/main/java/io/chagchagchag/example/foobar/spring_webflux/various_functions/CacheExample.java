package io.chagchagchag.example.foobar.spring_webflux.various_functions;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class CacheExample {
  public static void main(String[] args) {
    // (1)
    Flux<Object> flux = Flux.create(fluxSink -> {
      for(int i=0; i<3; i++){
        fluxSink.next(i);
        log.info("(created) " + i);
      }
      log.info("sink next 완료 (at Publisher)");
      fluxSink.complete();
    }).cache();

    // (2)
    flux.subscribe(
        v -> {log.info("v ::: " + v);},
        null,
        () -> {log.info("complete");}
    );

    // (3)
    flux.subscribe(
        v -> {log.info("v ::: " + v);},
        null,
        () -> {log.info("complete");}
    );
  }
}
