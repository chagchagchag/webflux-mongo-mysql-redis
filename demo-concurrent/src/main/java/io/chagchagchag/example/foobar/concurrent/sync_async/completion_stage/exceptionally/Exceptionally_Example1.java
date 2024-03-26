package io.chagchagchag.example.foobar.concurrent.sync_async.completion_stage.exceptionally;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Exceptionally_Example1 {
  public static void main(String[] args) {
    log.info("[start] main");
    stage()
        .thenApplyAsync(msg -> {
          log.info("thenApplyAsync");
          msg.charAt(-1);
          return msg.length();
        })
        .exceptionally(e -> {
          log.info("exceptionally, e = " + e.getMessage());
          return -1;
        })
        .thenAcceptAsync(resultCode -> {
          log.info("thenAcceptAsync, resultCode == " + resultCode);
        });

    sleep(1000);
    log.info("[end] main");
  }

  public static CompletionStage<String> stage(){
    return CompletableFuture.supplyAsync(() -> {
      log.info("CompletableFuture::supplyAsync 내부");
      return "안녕하세요.";
    });
  }

  @SneakyThrows
  public static void sleep(long ms){
    Thread.sleep(ms);
  }
}
