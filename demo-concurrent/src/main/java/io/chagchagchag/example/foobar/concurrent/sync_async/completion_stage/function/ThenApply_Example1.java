package io.chagchagchag.example.foobar.concurrent.sync_async.completion_stage.function;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThenApply_Example1 {
  @SneakyThrows
  public static void main(String[] args) {

  }

  public static CompletionStage<String> stage(){
    return CompletableFuture.supplyAsync(() -> {
      log.info("CompletableFuture 내부");
      return "안녕하세요";
    });
  }
}
