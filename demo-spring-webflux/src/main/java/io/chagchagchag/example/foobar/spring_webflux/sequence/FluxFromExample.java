package io.chagchagchag.example.foobar.spring_webflux.sequence;

import java.util.List;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class FluxFromExample {
  public static void main(String[] args) {
    Flux.fromIterable(List.of("MSFT", "NVDA", "SMCI"))
        .subscribe(v -> {log.info("value ==> " + v);});

    Flux.fromStream(IntStream.range(1,10).boxed())
        .subscribe(v -> {log.info("value ==> " + v);});

    Flux.fromArray(new Integer[]{1,2,3,4,5,6,7,8,9,10})
        .subscribe(v -> {log.info("value ==> " + v);});

    Flux.range(1,10)
        .subscribe(v -> {log.info("value ==> " + v);});
  }
}
