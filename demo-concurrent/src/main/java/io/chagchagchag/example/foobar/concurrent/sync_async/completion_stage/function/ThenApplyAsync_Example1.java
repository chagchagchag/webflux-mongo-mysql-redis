package io.chagchagchag.example.foobar.concurrent.sync_async.completion_stage.function;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThenApplyAsync_Example1 {
  @SneakyThrows
  public static void main(String[] args) {
    log.info("[start] main");
    CompletionStage<String> stage = stage();

    stage
        .thenApplyAsync(msg -> {
          String mapping = msg + " 오늘은 날씨가 흐리네요.";
          log.info("thenApplyAsync (1) ::: " + msg);
          return mapping;
        })
        .thenApplyAsync(msg -> {
          String mapping = msg + " 감기 조심하세요";
          log.info("thenApplyAsync (2) ::: " + msg);
          return mapping;
        })
        .thenApplyAsync(msg -> {
          int length = msg.length();
          log.info("length ::: " + length);
          return length;
        })
        .thenAcceptAsync(length -> {
          log.info("length ::: " + length);
        });

    Thread.sleep(300);
    log.info("[end] main");
  }

  public static CompletionStage<String> stage(){
    return CompletableFuture.supplyAsync(() -> {
      log.info("CompletableFuture::supplyAsync 내부");
      return "안녕하세요.";
    });
  }
}
