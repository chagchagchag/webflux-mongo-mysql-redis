package io.chagchagchag.example.foobar.spring_webflux.various_functions;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class FlatMap_Example {
  @SneakyThrows
  public static void main(String[] args) {
    Flux.range(1,3)
        .flatMap(v1 -> {
          return Flux.range(4,3)
              .map(v2 -> v1 + ", " + v2)
              .publishOn(Schedulers.parallel());
        })
        .doOnNext(v -> {
          log.info("doOnNext : " + v);
        })
        .subscribe();

    Thread.sleep(1000);
  }
}
