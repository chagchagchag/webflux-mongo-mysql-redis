package io.chagchagchag.example.foobar.spring_webflux.error_handling;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class OnErrorResume_Example2 {
  public static void main(String[] args) {
    Flux.just("배고파요", "밥먹어요", "배불러요")
        .onErrorResume(e -> Mono.just(errorMessage()))
        .subscribe(v -> log.info("v ::: " + v));
  }

  public static String errorMessage(){
    return "에러가 발생했어요";
  }
}
