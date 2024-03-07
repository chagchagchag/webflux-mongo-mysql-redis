package io.chagchagchag.example.foobar.reactive_streams_libraries.reactor.complete;

import io.chagchagchag.example.foobar.reactive_streams_libraries.reactor.SimpleSubscriber;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class FluxCompleteClient {
  public static void main(String[] args) {
    log.info("main function started");
    getItems()
        .subscribe(
            new SimpleSubscriber<>(Integer.MAX_VALUE)
        );
    log.info("main function end");
  }

  public static Flux<Integer> getItems(){
    return Flux.create(fluxSink -> {
      fluxSink.complete();
    });
  }
}
