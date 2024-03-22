package io.chagchagchag.example.foobar.spring_webflux.sequence;

import java.util.concurrent.CompletableFuture;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class FluxCreateExample {
  @SneakyThrows
  public static void main(String[] args) {
    Flux.create(fluxSink -> {
      var task1 = CompletableFuture.runAsync(() -> {
        for (int i=0; i<5; i++){
          fluxSink.next(i);
        }
      });

      var task2 = CompletableFuture.runAsync(() -> {
        for (int i=5; i<10; i++){
          fluxSink.next(i);
        }
      });

      CompletableFuture.allOf(task1, task2)
          .thenRun(fluxSink::complete);
    }).subscribe(
        v -> {log.info("value === " + v);},
        error -> {log.error("error === " + error);},
        () -> {log.info("complete");}
    );

    Thread.sleep(1000);
  }
}
