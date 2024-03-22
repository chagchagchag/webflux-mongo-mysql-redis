package io.chagchagchag.example.foobar.spring_webflux.sequence;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class EmptyExample {
  public static void main(String[] args) {
    Mono.empty()
        .subscribe(
            v -> {log.info("value === " +v);},
            null,
            () -> {log.info("complete");}
        );

    Flux.empty()
        .subscribe(
            v -> {log.info("value === " + v);},
            null,
            () -> {log.info("complete");}
        );
  }
}
