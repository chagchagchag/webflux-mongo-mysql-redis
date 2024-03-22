package io.chagchagchag.example.foobar.spring_webflux.concat_merge_mergesequential;

import java.time.Duration;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class MergeExample1 {
  @SneakyThrows
  public static void main(String[] args) {
    var flux1 = Flux.range(1,3)
        .doOnSubscribe(v -> {
          log.info("doOnSubscribe #1");
        })
        .delayElements(Duration.ofMillis(100));

    var flux2 = Flux.range(11, 3)
        .doOnSubscribe(v -> {
          log.info("doOnSubscribe #2");
        })
        .delayElements(Duration.ofMillis(100));

    Flux.merge(flux1, flux2)
        .doOnNext(v -> {log.info("doOnNext : " + v);})
        .subscribe();

    Thread.sleep(2000);
  }
}
