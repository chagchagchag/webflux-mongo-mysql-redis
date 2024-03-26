package io.chagchagchag.example.foobar.concurrent.sync_async.completable_future;

import java.util.concurrent.CompletableFuture;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AnyOf_Example1 {
  public static void main(String[] args) {
    long started = System.currentTimeMillis();
    CompletableFuture<String> f1 = delayedFuture(1000, "안녕하세요");
    CompletableFuture<String> f2 = delayedFuture(2000, "배고파요");
    CompletableFuture<String> f3 = delayedFuture(3000, "배불러요");

    CompletableFuture.anyOf(f1, f2, f3)
        .thenAcceptAsync(msg -> {
          log.info("--- after anyOf");
          try {
            log.info("msg : " + msg);
          } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
          }
        })
        .join();

    long endTime = System.currentTimeMillis();
    log.info("   took {} ms", (endTime - started));
  }

  public static CompletableFuture<String> delayedFuture(long ms, String msg){
    return CompletableFuture.supplyAsync(()->{
      log.info("delayedFuture, ms = " + ms);
      sleep(ms);
      return msg;
    });
  }

  @SneakyThrows
  public static void sleep(long ms){
    Thread.sleep(ms);
  }
}
