package io.chagchagchag.example.foobar.reactive_streams.publisher_subscriber.fixed_int;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FixedIntPublisher implements Flow.Publisher<FixedIntPublisher.Result> {

  public record Result(
      Integer value,
      Integer requestCount
  ){
  }

  @Override
  public void subscribe(Subscriber<? super Result> subscriber) {
    var numbers = Collections.synchronizedList(
        new ArrayList<>(List.of(1,2,3,4,5,6,7))
    );

    Iterator<Integer> iterator = numbers.iterator();
    var subscription = new IntSubscription(subscriber, iterator);
    subscriber.onSubscribe(subscription);
  }

  @RequiredArgsConstructor
  private static class IntSubscription implements Flow.Subscription{
    private final Subscriber<? super Result> subscriber;
    private final Iterator<Integer> numbers;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private final AtomicInteger count = new AtomicInteger(1);
    private final AtomicBoolean isCompleted = new AtomicBoolean(false);

    @Override
    public void request(long n) {
      executor.submit(()->{
        for(int i=0; i<n; i++){
          if(numbers.hasNext()){
            int number = numbers.next();
            numbers.remove();
            subscriber.onNext(new Result(number, count.get()));
          }
          else{
            var isChanged = isCompleted.compareAndSet(false, true);
            if(isChanged){
              executor.shutdown();
              subscriber.onComplete();
              isCompleted.set(true);
            }
            break;
          }
        }
        count.incrementAndGet();
      });
    }

    @Override
    public void cancel() {
      subscriber.onComplete();
    }
  }
}
