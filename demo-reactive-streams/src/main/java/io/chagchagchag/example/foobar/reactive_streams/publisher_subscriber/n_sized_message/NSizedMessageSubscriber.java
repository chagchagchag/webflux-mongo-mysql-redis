package io.chagchagchag.example.foobar.reactive_streams.publisher_subscriber.n_sized_message;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class NSizedMessageSubscriber<T> implements Flow.Subscriber<T>{
  private final Integer requestSize;
  private Flow.Subscription subscription;
  private int firstRequestSize = 1;
  private int nextCnt = 0;

  @Override
  public void onSubscribe(Subscription subscription) {
    this.subscription = subscription;
    this.subscription.request(firstRequestSize);
  }

  @Override
  public void onNext(T item) {
    log.info("(onNext) item = {}", item);
    // nextCnt++ 연산을 수행하고, requestSize 만큼 데이터가 한 차례 들어왔음을 판정
    if(nextCnt++ % requestSize == 0){
      // 지정한 requestSize 에 도달해서 onNext 를 수행
      log.info(">>> onNext : request 를 publisher 의 subscription 에 전송합니다.");
      this.subscription.request(requestSize);
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
