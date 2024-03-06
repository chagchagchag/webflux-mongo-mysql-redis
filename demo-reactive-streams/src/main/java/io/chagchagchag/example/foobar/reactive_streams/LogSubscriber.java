package io.chagchagchag.example.foobar.reactive_streams;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogSubscriber<T> implements Flow.Subscriber<T>{

  @Override
  public void onSubscribe(Subscription subscription) {
    subscription.request(1);
  }

  @Override
  public void onNext(Object item) {
    log.info("item : {}", item);
  }

  @Override
  public void onError(Throwable throwable) {
    log.error("error : {}", throwable.getMessage());
  }

  @Override
  public void onComplete() {
    log.info("complete");
  }
}
