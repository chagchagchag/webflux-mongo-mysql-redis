package io.chagchagchag.example.foobar.concurrent.sync_async.completable_future;

import java.util.concurrent.CompletableFuture;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Complete_Example1 {
  @SneakyThrows
  public static void main(String[] args) {
    CompletableFuture<String> future = new CompletableFuture<>();
    assert future.isDone() == false;

    boolean changed1 = future.complete("감기기운 있어요 흐흐흑");
    assert future.isDone() == true;
    assert changed1 == true;
    assert future.get().equals("감기기운 있어요 흐흐흑");

    boolean changed2 = future.complete("감기기운 있다구요");
    assert future.isDone() == true;
    assert changed2 == false;
    assert future.get().equals("감기기운 있어요 흐흐흑");
  }
}
