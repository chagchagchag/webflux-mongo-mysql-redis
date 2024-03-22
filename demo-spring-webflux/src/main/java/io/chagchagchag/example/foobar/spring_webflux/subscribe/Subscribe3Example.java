package io.chagchagchag.example.foobar.spring_webflux.subscribe;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

@Slf4j
public class Subscribe3Example {
  public static void main(String[] args) {
    var subscriber = new BaseSubscriber<String>(){
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
