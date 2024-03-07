package io.chagchagchag.example.foobar.reactive_streams_libraries.reactor.smallsize_request_backpressure;

import java.util.List;
import reactor.core.publisher.Flux;

public class SmallSizeBackpressureSubscribing_FluxClient {

  public static void main(String[] args) {
    getItems().subscribe(
        new SmallSizeBackpressureSubscriber<>()
    );
  }

  public static Flux<Integer> getItems(){
    return Flux.fromIterable(List.of(1,2,3,4,5));
  }
}
