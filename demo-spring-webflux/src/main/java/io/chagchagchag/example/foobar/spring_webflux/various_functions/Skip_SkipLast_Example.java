package io.chagchagchag.example.foobar.spring_webflux.various_functions;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class Skip_SkipLast_Example {
  @SneakyThrows
  public static void main(String[] args) {
    // 처음부터 n 번째까지를 스킵한 결과를 순회
    Flux.range(1, 10)
        .skip(3)
        .doOnNext(v -> {
          log.info("skip 하지 않은 요소 : " + v);
        })
        .subscribe();
    // 맨 끝에서부터 n 개를 skip 한 결과를 순회
    Flux.range(1, 1000)
        .skipLast(997)
        .doOnNext(v -> {
          log.info("skip 하지 않은 요소 " + v);
        })
        .subscribe();

    Thread.sleep(2000);
  }
}
