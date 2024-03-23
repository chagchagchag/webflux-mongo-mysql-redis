package io.chagchagchag.example.foobar.spring_webflux.error_handling;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class ErrorConsumer_Example {

  public static void main(String[] args) {
    Flux.error(new RuntimeException("error"))
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
