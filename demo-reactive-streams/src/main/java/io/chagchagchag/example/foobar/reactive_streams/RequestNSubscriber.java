package io.chagchagchag.example.foobar.reactive_streams;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class RequestNSubscriber<T> implements Flow.Subscriber<T>{
  private final Integer n;
  private Flow.Subscription subscription;
  private int count = 0;

  @Override
  public void onSubscribe(Subscription subscription) {
    this.subscription = subscription;
    this.subscription.request(1);
  }

  @Override
  public void onNext(T item) {
    log.info("(onNext) item = {}", item);

    if(count++ % n == 0){
      log.info("(onNext) send request to publisher's subscription");
      this.subscription.request(n);
    }
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
