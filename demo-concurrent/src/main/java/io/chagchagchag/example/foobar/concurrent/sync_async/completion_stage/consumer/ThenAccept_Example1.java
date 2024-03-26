package io.chagchagchag.example.foobar.concurrent.sync_async.completion_stage.consumer;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThenAccept_Example1 {
  @SneakyThrows
  public static void main(String[] args) {
    log.info("[start] main");
    CompletionStage<String> stage = stage();

    stage
        .thenAccept(msg -> {
          log.info("[thenAccept (1)] msg :: " + msg + "");
        })
        .thenAccept(msg -> {
          log.info("[thenAccept (2)] msg :: " + msg + "");
        });

    Thread.sleep(1000);
    log.info("[end] main");
  }

  @SneakyThrows
  public static CompletionStage<String> stage(){
    var future = CompletableFuture.supplyAsync(() -> {
      log.info("CompletableFuture 내부");
      return "안녕하세요";
    });

    Thread.sleep(1000);
    return future;
  }
}
