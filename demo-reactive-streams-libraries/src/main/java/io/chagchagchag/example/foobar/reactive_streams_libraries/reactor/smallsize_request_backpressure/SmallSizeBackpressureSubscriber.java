package io.chagchagchag.example.foobar.reactive_streams_libraries.reactor.smallsize_request_backpressure;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Slf4j
public class SmallSizeBackpressureSubscriber<T> implements Subscriber<T> {
  private final Integer requestSize = 1;
  private Subscription subscription;

  @Override
  public void onSubscribe(Subscription s) {
    this.subscription = s;
    log.info("(subscribe) --- ");
    s.request(requestSize);
    log.info(" >>> subscriber.request({})", requestSize);
  }

  @SneakyThrows
  @Override
  public void onNext(T t) {
    log.info("(next) item : {}", t);
    Thread.sleep(1000);
    // BigRequestSizeSubscriber 예제에서는 onNext() 내부에서 아래와 같이 request 를 하지 않았다는 점과 비교해보셔야 합니다.
    subscription.request(requestSize);
    log.info("requestSize : {}", requestSize);
  }

  @Override
  public void onError(Throwable t) {
    log.error("error : {}", t.getMessage());
  }

  @Override
  public void onComplete() {
    log.info("=== (complete) ===");
  }
}
