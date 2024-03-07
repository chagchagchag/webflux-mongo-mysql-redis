package io.chagchagchag.example.foobar.reactive_streams_libraries.reactor.flux_to_mono;

import io.chagchagchag.example.foobar.reactive_streams_libraries.reactor.SimpleSubscriber;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class FluxToMonoClient_Flux_collectList {
  public static void main(String[] args) {
    log.info("main function started");
    Mono
        .from(getItems().collectList())
        .subscribe(new SimpleSubscriber<>(Integer.MAX_VALUE));
    log.info("main function end");
  }

  public static Flux<Integer> getItems(){
    return Flux.fromIterable(List.of(1,2,3,4,5));
  }
}
