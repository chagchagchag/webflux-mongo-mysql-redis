package io.chagchagchag.example.foobar.spring_webflux.subscribe;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.util.context.Context;

@Slf4j
public class Subscribe2Example {
  public static void main(String[] args) {
    Flux.fromIterable(List.of("배고파요", "밥먹어요", "배불러요"))
        .subscribe(
            v -> {log.info("value === " + v);},
            error -> {log.error("error === " + error);},
            () -> {log.info("complete");},
            Context.empty()
        );
  }
}
