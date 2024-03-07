package io.chagchagchag.example.foobar.reactive_streams_libraries.reactor.bigsize_request;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class BigRequestSizeSubscribing_FluxClient {
  public static void main(String[] args) {
    log.info("main function started --- ");
    getItems().subscribe(new BigRequestSizeSubscriber<>(Integer.MAX_VALUE));
    log.info("main function end --- ");
  }

  private static Flux<Integer> getItems(){
    return Flux.fromIterable(List.of(100,200,300,400,500));
  }
}
