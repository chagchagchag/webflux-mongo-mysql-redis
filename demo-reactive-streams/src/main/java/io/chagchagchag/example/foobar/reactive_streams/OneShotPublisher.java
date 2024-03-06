package io.chagchagchag.example.foobar.reactive_streams;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import lombok.RequiredArgsConstructor;

public class OneShotPublisher implements Publisher<Boolean> {
  private final ExecutorService executorService = ForkJoinPool.commonPool();
  private boolean subscribed;

  @Override
  public synchronized void subscribe(Subscriber<? super Boolean> subscriber){
    if(subscribed)
      subscriber.onError(new IllegalStateException());
    else{
      subscribed = true;
      subscriber.onSubscribe(new OneShotSubscription(subscriber, executorService));
    }
  }

  @RequiredArgsConstructor
  static class OneShotSubscription implements Subscription {
    private final Subscriber<? super Boolean> subscriber;
    private final ExecutorService executor;
    private Future<?> future; // to allow cancellation
    private boolean completed;

    @Override
    public void request(long n) {
      if (!completed) {
        completed = true;
        if (n <= 0) {
          IllegalArgumentException ex = new IllegalArgumentException();
          executor.execute(() -> subscriber.onError(ex));
        } else {
          future = executor.submit(() -> {
            subscriber.onNext(Boolean.TRUE);
            subscriber.onComplete();
          });
        }
      }
    }

    @Override
    public void cancel() {
      completed = true;
      if (future != null) future.cancel(false);
    }
  }
}
