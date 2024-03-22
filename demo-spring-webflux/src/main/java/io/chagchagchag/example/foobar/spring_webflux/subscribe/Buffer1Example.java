package io.chagchagchag.example.foobar.spring_webflux.subscribe;

import java.util.List;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

@Slf4j
public class Buffer1Example {
  public static void main(String[] args) {
    var subscriber = new BaseSubscriber<List<Integer>>(){
      @Override
      protected void hookOnSubscribe(Subscription subscription) {
        request(2);
      }

      @Override
      protected void hookOnNext(List<Integer> value) {
        log.info("value = " + value);
      }

      @Override
      protected void hookOnComplete() {
        log.info("complete");
      }
    };

    Flux.fromStream(IntStream.range(0,20).boxed())
        .buffer(3)
        .subscribe(subscriber);
  }
}
