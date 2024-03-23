package io.chagchagchag.example.foobar.spring_webflux.various_functions;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class Map_MapNotNull_Example {
  public static void main(String[] args) {
    Flux.range(1,3)
        .map(v -> v*100)
        .doOnNext(v -> {
          log.info("doOnNext : " + v);
        })
        .subscribe();

    Flux.range(1,3)
        .mapNotNull(v -> {
          if(v % 2 == 0) return v;
          return null;
        })
        .doOnNext(v -> {
          log.info("doOnNext : " + v);
        })
        .subscribe();
  }
}
