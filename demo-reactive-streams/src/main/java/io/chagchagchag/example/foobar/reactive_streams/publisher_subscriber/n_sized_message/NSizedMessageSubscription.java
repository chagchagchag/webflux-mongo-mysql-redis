package io.chagchagchag.example.foobar.reactive_streams.publisher_subscriber.n_sized_message;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NSizedMessageSubscription implements Flow.Subscription{
  private final Subscriber<? super Message> subscriber;
  private final Iterator<String> messages;
  private final ExecutorService executorService = Executors.newSingleThreadExecutor();

  // 백프레셔 요청 횟수 기록
  private final AtomicInteger requestCnt = new AtomicInteger(1);
  private final AtomicBoolean isCompleted = new AtomicBoolean(false);

  @Override
  public void request(long requestSize) {
    executorService.submit(()->{
      // requestSize 만큼 데이터를 처리
      for(int i=0; i<requestSize; i++){
        if(messages.hasNext()){
          String message = messages.next();
          subscriber.onNext(new Message(message, requestCnt.get()));
        }
        else{ // 더 이상 보낼 데이터가 없다. 종료 진행
          // 현재 isCompleted 가 false 일때 true 로 바꿔준다.
          var isChanged = isCompleted.compareAndSet(false, true);

          if(isChanged){
            executorService.shutdown(); // executorService 회수
            subscriber.onComplete();  // subscriber 에 onComplete 이벤트 emit
            isCompleted.set(true);  // isCompleted 를 true 로 세팅
          }
          break;
        }
      }

      requestCnt.incrementAndGet();
    });
  }

  @Override
  public void cancel() {
    // cancel 시에는 complete 수행
    subscriber.onComplete();
  }
}
