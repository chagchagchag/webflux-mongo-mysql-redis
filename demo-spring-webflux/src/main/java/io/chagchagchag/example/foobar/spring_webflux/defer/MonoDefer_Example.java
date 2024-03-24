package io.chagchagchag.example.foobar.spring_webflux.defer;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class MonoDefer_Example {
  public static void main(String[] args) {
    Mono.defer(() -> {
      return Mono.just("안녕하세요");
    }).subscribe(message -> {
      log.info("message == " + message);
    });
  }
}
