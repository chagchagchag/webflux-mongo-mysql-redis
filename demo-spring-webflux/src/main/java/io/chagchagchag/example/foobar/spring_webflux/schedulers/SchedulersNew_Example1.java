package io.chagchagchag.example.foobar.spring_webflux.schedulers;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class SchedulersNew_Example1 {
  @SneakyThrows
  public static void main(String[] args) {
    for(int i=0; i<100; i++){
      var newSingle = Schedulers.newSingle("싱글스레드");
      final int index = i;
      Flux.create(fluxSink -> {
        log.info("index == " + index);
        fluxSink.next(index);
      })
      .subscribeOn(newSingle)
      .subscribe(v -> {
        log.info("v ::: " + v);
        newSingle.dispose();
      });
    }
  }
}
