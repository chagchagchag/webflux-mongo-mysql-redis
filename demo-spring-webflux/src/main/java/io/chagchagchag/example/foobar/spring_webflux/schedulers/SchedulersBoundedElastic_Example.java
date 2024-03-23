package io.chagchagchag.example.foobar.spring_webflux.schedulers;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class SchedulersBoundedElastic_Example {
  @SneakyThrows
  public static void main(String[] args) {
    for(int i=0; i<300; i++){
      final int index = i;
      Flux.create(fluxSink -> {
        log.info("index == " + index);
        fluxSink.next(index);
      })
      .subscribeOn(Schedulers.boundedElastic())
      .subscribe(
          v -> {
            log.info("v ::: " + v);
          }
      );
    }

    Thread.sleep(2000);
  }
}
