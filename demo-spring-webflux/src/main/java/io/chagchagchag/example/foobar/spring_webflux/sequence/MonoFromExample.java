package io.chagchagchag.example.foobar.spring_webflux.sequence;

import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class MonoFromExample {
  public static void main(String[] args) {
    Mono.fromCallable(() -> {
      return "삼성전자";
    }).subscribe(v -> {
      log.info("fromCallable 에서 받은 value === " + v);
    });

    Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
      return "삼성전자";
    })).subscribe(v -> {
      log.info("fromFuture 로부터 받은 value === " + v);
    });

    Mono.fromSupplier(() -> {
      return "삼성전자";
    }).subscribe(v -> {
      log.info("fromSupplier 로부터 받은 value === " + v);
    });

    Mono.fromRunnable(() -> {
      log.info("do some runnable");
    }).subscribe(
        null, null, () -> {log.info("fromRunnable Complete.");}
    );
  }
}
