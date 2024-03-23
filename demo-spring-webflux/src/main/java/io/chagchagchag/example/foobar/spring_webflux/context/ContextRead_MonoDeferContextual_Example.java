package io.chagchagchag.example.foobar.spring_webflux.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class ContextRead_MonoDeferContextual_Example {
  public static void main(String[] args) {
    Flux.just("누구시죠")
        .flatMap(v -> {
          return Mono.deferContextual(contextView -> {
            String message = contextView.get("message");
            log.info("message ::: " + message);
            return Mono.just(v);
          });
        })
        .contextWrite(context -> context.put("message", "안녕하세요"))
        .subscribe();
  }
}
