package io.chagchagchag.example.foobar.spring_webflux.defer;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class MonoDeferFlatMap_Example {
  public static void main(String[] args) {
    var userName = "Mono.defer";
    Mono.just(userName)
        .flatMap(v -> Mono.defer(() -> {
          return Mono.just(userName + "님, 안녕하세요. 반가워요");
        })).subscribe(v -> {
          log.info("message === " + v);
        });
  }
}
