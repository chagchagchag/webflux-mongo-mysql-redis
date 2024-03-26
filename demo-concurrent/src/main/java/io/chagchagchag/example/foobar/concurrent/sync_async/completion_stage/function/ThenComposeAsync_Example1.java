package io.chagchagchag.example.foobar.concurrent.sync_async.completion_stage.function;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThenComposeAsync_Example1 {
  @SneakyThrows
  public static void main(String[] args) {
    CompletionStage<String> stage = stage();
    stage
        .thenComposeAsync(msg -> {
          CompletionStage<String> result = append(msg, " 안녕하세요");
          return result;
        })
        .thenComposeAsync(msg -> {
          CompletionStage<Integer> result = length(msg);
          return result;
        })
        .thenAcceptAsync(value -> {
          log.info("thenAcceptAsync, value = " + value);
        });

    Thread.sleep(1000);
  }

  public static CompletionStage<String> stage(){
    return CompletableFuture.supplyAsync(() -> {
      log.info("CompletableFuture::supplyAsync 내부");
      return "안녕하세요.";
    });
  }

  public static CompletionStage<String> append(String source, String postfix){
    return CompletableFuture.supplyAsync(() -> {
      sleep(100);
      return source + postfix;
    });
  }

  public static CompletionStage<Integer> length(String source){
    return CompletableFuture.supplyAsync(() -> {
      sleep(100);
      return source.length();
    });
  }

  @SneakyThrows
  public static void sleep(long ms){
    try{
      Thread.sleep(100);
    }
    catch (Exception e){
      e.printStackTrace();
    }
  }
}
