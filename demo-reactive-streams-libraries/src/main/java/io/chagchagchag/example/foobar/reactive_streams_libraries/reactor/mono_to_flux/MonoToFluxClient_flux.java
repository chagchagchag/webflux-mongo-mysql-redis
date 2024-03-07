package io.chagchagchag.example.foobar.reactive_streams_libraries.reactor.mono_to_flux;

import io.chagchagchag.example.foobar.reactive_streams_libraries.reactor.SimpleSubscriber;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class MonoToFluxClient_flux {
  public static void main(String[] args) {
    log.info("main function started");

    getItems()
        .flux()
        .subscribe(new SimpleSubscriber<>(Integer.MAX_VALUE));

    log.info("main function end");
  }

  public static Mono<List<Integer>> getItems(){
    return Mono.just(List.of(1,2,3,4,5));
  }
}
