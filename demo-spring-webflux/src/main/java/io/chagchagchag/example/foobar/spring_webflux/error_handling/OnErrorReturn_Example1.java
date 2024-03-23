package io.chagchagchag.example.foobar.spring_webflux.error_handling;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class OnErrorReturn_Example1 {
  public static void main(String[] args) {
    Flux.error(new RuntimeException("error"))
        .onErrorReturn("에러가 발생했어요")
        .subscribe(v -> {
          log.info("v ::: " + v);
        });
  }
}
