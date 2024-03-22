package io.chagchagchag.example.foobar.spring_webflux.delay_elements;

import java.time.Duration;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class DelayElementsExample1 {
  @SneakyThrows
  public static void main(String[] args) {
    Flux.create(fluxSink -> {
      for(int i=1; i<=5; i++){
        try{
          Thread.sleep(10);
        }
        catch (InterruptedException e){
          e.printStackTrace();
          throw new IllegalStateException(e);
        }
        fluxSink.next(i);
      }
      fluxSink.complete();
    })
    .delayElements(Duration.ofMillis(500))
    .doOnNext(v -> {
      log.info("doOnNext = " + v);
    })
    .subscribeOn(Schedulers.single())
    .subscribe();

    Thread.sleep(5000);
  }
}
