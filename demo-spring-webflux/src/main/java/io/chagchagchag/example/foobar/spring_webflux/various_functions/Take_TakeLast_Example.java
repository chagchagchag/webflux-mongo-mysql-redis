package io.chagchagchag.example.foobar.spring_webflux.various_functions;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class Take_TakeLast_Example {
  @SneakyThrows
  public static void main(String[] args) {
    Flux.range(1,100000)
        .take(5)
        .doOnNext(v -> {
          log.info("taken item = " + v);
        })
        .subscribe();

    Flux.range(1, 100)
        .takeLast(10)
        .doOnNext(v -> {
          log.info("taken item = " + v);
        })
        .subscribe();

    Thread.sleep(2000);
  }
}
