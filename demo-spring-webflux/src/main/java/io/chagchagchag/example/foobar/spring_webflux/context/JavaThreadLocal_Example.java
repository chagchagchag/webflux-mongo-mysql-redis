package io.chagchagchag.example.foobar.spring_webflux.context;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class JavaThreadLocal_Example {
  @SneakyThrows
  public static void main(String[] args) {
    ThreadLocal<String> threadLocal = new ThreadLocal<>();
    threadLocal.set("배고파요");

    Flux.create(fluxSink -> {
      log.info("threadLocal : " + threadLocal.get());
      fluxSink.next(5);
    })
    .publishOn(Schedulers.parallel())
    .map(v -> {
      log.info("threadLocal : " + threadLocal.get());
      return v;
    })
    .publishOn(Schedulers.boundedElastic())
    .map(v -> {
      log.info("threadLocal : " + threadLocal.get());
      return v;
    })
    .subscribeOn(Schedulers.single())
    .subscribe();

    Thread.sleep(2000);
  }
}
