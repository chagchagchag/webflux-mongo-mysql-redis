package io.chagchagchag.example.foobar.concurrent.sync_async.completable_future;

import java.util.concurrent.CompletableFuture;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SupplyAsync_Example1 {
  @SneakyThrows
  public static void main(String[] args) {
    CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
      sleep(100);
      return "감기기운이 있어요";
    });

    assert completableFuture.isDone() == false;

    sleep(1000);
    assert completableFuture.isDone();
    assert completableFuture.get() == "감기기운이 있어요";
  }

  @SneakyThrows
  public static void sleep(long ms){
    Thread.sleep(ms);
  }
}
