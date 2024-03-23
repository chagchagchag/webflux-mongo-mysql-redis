package io.chagchagchag.example.foobar.spring_webflux.publish_on_subscribe_on;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class PublishOn_Example1 {
  @SneakyThrows
  public static void main(String[] args) {
    Flux.create(fluxSink -> {
      for(int i=0; i<7; i++){
        log.info("i == " + i);
        fluxSink.next(i);
      }
    })
    .publishOn(Schedulers.single())
    .doOnNext(element -> {
      log.info("doOnNext1 :: " + element);
    })
    .publishOn(Schedulers.boundedElastic())
    .doOnNext(element -> {
      log.info("doOnNext2 :: " + element);
    })
    .subscribe(v -> {
      log.info("v ::: " + v);
    });

    Thread.sleep(2000);
  }
}
