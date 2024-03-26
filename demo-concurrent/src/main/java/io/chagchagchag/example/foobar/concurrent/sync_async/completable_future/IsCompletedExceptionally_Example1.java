package io.chagchagchag.example.foobar.concurrent.sync_async.completable_future;

import java.util.concurrent.CompletableFuture;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IsCompletedExceptionally_Example1 {
  @SneakyThrows
  public static void main(String[] args) {
    CompletableFuture<String> exceptionFuture = CompletableFuture.supplyAsync(() -> {
      return "안녕하세요".substring(5, 6);
    });

    Thread.sleep(100);
    assert exceptionFuture.isDone();
    assert exceptionFuture.isCompletedExceptionally();
  }
}
