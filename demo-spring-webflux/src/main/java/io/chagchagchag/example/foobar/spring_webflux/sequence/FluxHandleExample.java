package io.chagchagchag.example.foobar.spring_webflux.sequence;

import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class FluxHandleExample {
  public static void main(String[] args) {
    Flux.fromStream(IntStream.range(0,11).boxed())
        .handle((v, sink) -> {
          if(v%2 == 0) sink.next(v);
        }).subscribe(
            v -> {log.info("value === " + v);},
            error -> {log.error("error === " + error);},
            () -> {log.info("complete");}
        );
  }
}
