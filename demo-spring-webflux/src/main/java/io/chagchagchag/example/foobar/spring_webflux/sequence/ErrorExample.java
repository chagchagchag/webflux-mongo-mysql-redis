package io.chagchagchag.example.foobar.spring_webflux.sequence;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class ErrorExample {

  public static void main(String[] args) {
    Mono.error(new IllegalArgumentException("어머, 에러에요"))
        .subscribe(
            v -> {log.info("v ::: " + v);},
            error -> {log.error("error ::: " + error);}
        );

    Flux.error(new IllegalArgumentException("어머, 에러에요"))
        .subscribe(
            v -> {log.info("value ::: " + v);},
            error -> {log.error("error ::: " + error);}
        );
  }
}
