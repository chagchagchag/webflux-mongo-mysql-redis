package io.chagchagchag.example.foobar.spring_webflux.sequence;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class FluxGenerateExample2 {
  public static void main(String[] args) {
    Flux.generate(
        () -> 0,
        (state, sink) -> {
          sink.next(state);
          sink.next(state);
          if(state == 9) sink.complete();

          return state + 1;
        }
    ).subscribe(
        v -> { log.info("value === " + v); },
        error -> { log.error("error === " + error); },
        () -> { log.info("complete"); }
    );
  }
}
