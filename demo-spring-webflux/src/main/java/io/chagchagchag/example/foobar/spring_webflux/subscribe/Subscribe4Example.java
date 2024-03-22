package io.chagchagchag.example.foobar.spring_webflux.subscribe;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

@Slf4j
public class Subscribe4Example {
  public static void main(String[] args) {
    var subscriber = new BaseSubscriber<String>(){
      @Override
      protected void hookOnSubscribe(Subscription subscription) {
        request(1); // backpressure 함수인 reuest(n) 호출
      }

      @Override
      protected void hookOnNext(String value) {
        log.info("value === " + value);
      }

      @Override
      protected void hookOnComplete() {
        log.info("complte");
      }
    };

    Flux.fromIterable(List.of("배고파요", "밥먹어요", "배불러요"))
        .subscribe(subscriber);
  }
}
