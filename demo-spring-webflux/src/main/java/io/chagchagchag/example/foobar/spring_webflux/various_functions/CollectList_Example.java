package io.chagchagchag.example.foobar.spring_webflux.various_functions;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class CollectList_Example {
  public static void main(String[] args) {
    Flux.range(1, 10)
        .doOnNext(v -> {
          log.info("(before) doOnNext >> " + v);
        })
        .collectList()
        .doOnNext(v -> {
          log.info("(after) doOnNext >> " + v);
        })
        .subscribe();
  }
}
