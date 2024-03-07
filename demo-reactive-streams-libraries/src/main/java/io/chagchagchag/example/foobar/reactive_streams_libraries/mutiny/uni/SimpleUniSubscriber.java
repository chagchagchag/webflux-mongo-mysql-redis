package io.chagchagchag.example.foobar.reactive_streams_libraries.mutiny.uni;

import io.smallrye.mutiny.subscription.UniSubscriber;
import io.smallrye.mutiny.subscription.UniSubscription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class SimpleUniSubscriber<T> implements UniSubscriber<T> {
  private final Integer count;
  private UniSubscription subscription;
  @Override
  public void onSubscribe(UniSubscription uniSubscription) {
    this.subscription = uniSubscription;
    subscription.request(1);
    log.info("subscribe");
  }

  @Override
  public void onItem(T t) {
    log.info("item : {}", t);
  }

  @Override
  public void onFailure(Throwable throwable) {
    log.error("error, message = {}", throwable.getMessage());
  }
}
