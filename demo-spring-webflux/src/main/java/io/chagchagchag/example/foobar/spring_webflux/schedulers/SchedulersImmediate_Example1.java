package io.chagchagchag.example.foobar.spring_webflux.schedulers;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class SchedulersImmediate_Example1 {
  public static void main(String[] args) {
    Flux.create(fluxSink -> {
      for(int i=0; i<=3; i++){
        log.info("i == " + i);
        fluxSink.next(i);
      }
    })
    .subscribeOn(Schedulers.immediate())
    .subscribe(
        v -> {
          log.info("v == " + v);
        }
    );
  }
}
