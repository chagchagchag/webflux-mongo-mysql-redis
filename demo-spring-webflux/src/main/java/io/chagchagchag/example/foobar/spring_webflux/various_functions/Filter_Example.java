package io.chagchagchag.example.foobar.spring_webflux.various_functions;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class Filter_Example {
  public static void main(String[] args) {
    Flux.range(1,30)
        .filter(v -> v%3 == 0)
        .doOnNext(v -> {
          log.info("doOnNext : " + v);
        })
        .subscribe();
  }
}
