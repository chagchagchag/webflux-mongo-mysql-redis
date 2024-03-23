package io.chagchagchag.example.foobar.spring_webflux.error_handling;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class DoOnError_Example {
  public static void main(String[] args) {
    Flux.error(new RuntimeException("에러가 났어요"))
        .doOnError(error -> {
          log.info("doOnError ::: " + error);
        })
        .subscribe(
            v -> {
              log.info("v ::: " + v);
            },
            error -> {
              log.info("error ::: " + error);
            }
        );
  }
}
