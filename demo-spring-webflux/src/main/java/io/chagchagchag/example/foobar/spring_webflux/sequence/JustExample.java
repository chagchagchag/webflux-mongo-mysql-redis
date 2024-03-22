package io.chagchagchag.example.foobar.spring_webflux.sequence;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class JustExample {
  public static void main(String[] args) {
    Mono.just("안녕하세요")
        .subscribe(v -> {
          log.info(">>> {}", v);
        });

    Flux.just("MSFT", "NVDA", "SMCI")
        .subscribe(v -> {
          log.info(">>> {}", v);
        });
  }
}
