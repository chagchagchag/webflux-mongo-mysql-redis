package io.chagchagchag.example.foobar.concurrent.sync_async.completable_future;

import java.util.concurrent.CompletableFuture;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RunAsync_Example1 {
  @SneakyThrows
  public static void main(String[] args) {
    var completableFuture = CompletableFuture.runAsync(()-> {
      sleep(100);
    });

    assert !completableFuture.isDone();

    sleep(1000);
    assert completableFuture.isDone();
    assert completableFuture.get() == null;
  }

  @SneakyThrows
  public static void sleep(long ms){
    Thread.sleep(ms);
  }
}
