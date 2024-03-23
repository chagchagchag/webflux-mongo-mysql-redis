package io.chagchagchag.example.foobar.spring_webflux.schedulers;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class SchedulersSingle_Example1 {
  @SneakyThrows
  public static void main(String[] args) {
    for(int i=0; i<15; i++){
      final int index = i;
      Flux.create(fluxSink -> {
        log.info("index == " + index);
        fluxSink.next(index);
      }).subscribeOn(
          Schedulers.single()
      ).subscribe(
          v -> {
            log.info("v ::: " + v);
          }
      );
    }

    Thread.sleep(2000);
  }
}
