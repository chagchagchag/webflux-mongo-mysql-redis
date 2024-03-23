package io.chagchagchag.example.foobar.spring_webflux.error_handling;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class NoErrorHanding_Example {
  public static void main(String[] args) {
    Flux.create(fluxSink -> {
      try{
        Thread.sleep(1000);
      } catch (InterruptedException e){
        throw new RuntimeException(e);
      }
      fluxSink.error(new RuntimeException("에러가 발생했어요"));
    }).subscribe();
  }
}
