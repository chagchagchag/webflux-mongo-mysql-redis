package io.chagchagchag.example.foobar.concurrent.sync_async.completion_stage.runnable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThenRunAsync_Example {
  public static void main(String[] args) {
    log.info("[start] main");
    CompletionStage<String> stage = stage();

    stage
        .thenRunAsync(() -> {
          log.info("thenRunAsync() (1)");
        })
        .thenRunAsync(() -> {
          log.info("thenRunAsync() (2)");
        })
        .thenAcceptAsync(v -> {
          log.info("thenAcceptAsync :: " + v);
        });

    log.info("[end] main");
    sleep(100);
  }

  public static CompletionStage<String> stage(){
    var future = CompletableFuture.supplyAsync(() -> {
      log.info("CompletableFuture 내부");
      return "안녕하세요";
    });
    return future;
  }

  @SneakyThrows
  public static void sleep(long ms){
    Thread.sleep(ms);
  }
}
