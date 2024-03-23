package io.chagchagchag.example.foobar.spring_webflux.context;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class ContextWrite_Example {
  @SneakyThrows
  public static void main(String[] args) {
    Flux.just(1)
        .flatMap(v -> contextLog(v, "(1)"))
        .contextWrite(context -> context.put("message", "배고파요"))
        .flatMap(v -> contextLog(v, "(2)"))
        .contextWrite(context -> context.put("message", "배불러요"))
        .flatMap(v -> contextLog(v, "(3)"))
        .subscribe();
  }

  public static <T> Mono<T> contextLog(T t, String v){
    return Mono.deferContextual(c -> {
      log.info("v === {} , context === {}", v, c);
      return Mono.just(t);
    });
  }
}
