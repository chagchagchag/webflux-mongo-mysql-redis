package io.chagchagchag.example.foobar.spring_webflux.error_handling;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class OnErrorComplete_Example1 {
  public static void main(String[] args) {
    Flux.create(fluxSink -> {
      fluxSink.next(1);
      fluxSink.next(2);
      fluxSink.error(new RuntimeException("에러가 발생했어요"));
    }).onErrorComplete()
        .subscribe(
            v -> {log.info("v ::: " + v);},
            e -> {log.info("e ::: " + e);},
            () -> {log.info("complete");}
        );
  }
}
