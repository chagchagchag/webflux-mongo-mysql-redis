package io.chagchagchag.example.foobar.spring_webflux.subscribe;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class Subscribe1Example {
  public static void main(String[] args) {
    Flux.fromIterable(List.of("배고파요", "밥먹어요", "배불러요"))
        .doOnNext(v -> { save(v);})
        .subscribe();
  }

  public static void save(String s){
    log.info("SAVE DATA >>> " + s);
  }
}
