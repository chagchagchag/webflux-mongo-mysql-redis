package io.chagchagchag.example.foobar.reactive_streams_libraries.reactor.bigsize_request;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Slf4j
@RequiredArgsConstructor
public class BigRequestSizeSubscriber<T> implements Subscriber<T> {
  private final Integer requestSize;

  @Override
  public void onSubscribe(Subscription s) {
    log.info("(subscribe) --- ");
    s.request(requestSize);
    log.info(" >>> subscriber.request({})", requestSize);
  }

  @SneakyThrows
  @Override
  public void onNext(T t) {
    log.info("(next) item : {}", t);
    Thread.sleep(1000);
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
