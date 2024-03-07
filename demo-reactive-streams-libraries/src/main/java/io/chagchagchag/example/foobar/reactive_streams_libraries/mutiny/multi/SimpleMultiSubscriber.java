package io.chagchagchag.example.foobar.reactive_streams_libraries.mutiny.multi;

import io.smallrye.mutiny.subscription.MultiSubscriber;
import java.util.concurrent.Flow.Subscription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class SimpleMultiSubscriber <T> implements MultiSubscriber<T> {
  private final Integer requestSize;
  @Override
  public void onItem(T t) {
    log.info("item == {}", t);
  }

  @Override
  public void onFailure(Throwable throwable) {
    log.error("fail, mesage = {}", throwable.getMessage());
  }

  @Override
  public void onCompletion() {
    log.info("complete");
  }

  @Override
  public void onSubscribe(Subscription subscription) {
    subscription.request(requestSize);
    log.info("subscribe");
  }
}
