package io.chagchagchag.example.foobar.spring_webflux.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

@Slf4j
public class SubscribeOnContext_Example1 {
  public static void main(String[] args) {
    var initialContext = Context.of("message", "배불러요");
    Flux.just("hello")
        .flatMap(v -> contextLog(v, "(1)"))
        .contextWrite(context -> context.put("message", "배고파요"))
        .flatMap(v -> contextLog(v, "(2)"))
        .subscribe(null, null, null, initialContext);
  }

  public static <T> Mono<T> contextLog(T t, String v){
    return Mono.deferContextual(c -> {
      log.info("v === {} , context === {}", v, c);
      return Mono.just(t);
    });
  }
}
