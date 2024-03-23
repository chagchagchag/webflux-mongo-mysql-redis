package io.chagchagchag.example.foobar.spring_webflux.various_functions;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class DoOnFunctions_Example {
  public static void main(String[] args) {
    Flux.range(1,3)
        .map(v -> v*2)
        .doOnNext(v -> {
          log.info("doOnNext : " + v);
        })
        .doOnComplete(() -> {
          log.info("doOnComplete");
        })
        .doOnSubscribe(subscription -> {
          log.info("doOnSubscribe");
        })
        .doOnRequest(v -> {
          log.info("doOnRequest : " + v);
        })
        .map(v -> v/2)
        .subscribe();
  }
}
