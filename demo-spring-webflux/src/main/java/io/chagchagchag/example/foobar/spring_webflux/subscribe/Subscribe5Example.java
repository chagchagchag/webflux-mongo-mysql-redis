package io.chagchagchag.example.foobar.spring_webflux.subscribe;

import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

@Slf4j
public class Subscribe5Example {
  public static void main(String[] args) {
    var subscriber = new BaseSubscriber<Integer>(){
      @Override
      protected void hookOnNext(Integer value) {
        log.info("value == " + value);
      }

      @Override
      protected void hookOnComplete() {
        log.info("completed");
      }
    };

    Flux.fromStream(IntStream.range(0, 20).boxed())
        .take(11, true)
        .subscribe(subscriber);
  }
}
