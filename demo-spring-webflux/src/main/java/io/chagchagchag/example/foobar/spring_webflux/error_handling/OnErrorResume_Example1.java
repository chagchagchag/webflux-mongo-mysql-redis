package io.chagchagchag.example.foobar.spring_webflux.error_handling;

import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

@Slf4j
public class OnErrorResume_Example1 {
  public static void main(String[] args) {
    Flux.error(new RuntimeException("error"))
        .onErrorResume(new Function<Throwable, Publisher<String>>() {
          @Override
          public Publisher<String> apply(Throwable throwable) {
            return Flux.just("배고파요", "밥먹어요", "배불러요");
          }
        })
        .subscribe(v -> {
          log.info("v ::: " + v);
        });
  }
}
