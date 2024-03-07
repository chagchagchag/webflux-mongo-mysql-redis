package io.chagchagchag.example.foobar.reactive_streams_libraries.reactor.mono;

import io.chagchagchag.example.foobar.reactive_streams_libraries.reactor.SimpleSubscriber;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class SimpleMonoClient {
  @SneakyThrows
  public static void main(String[] args) {
    log.info("main function started");
    getITems().subscribe(
        new SimpleSubscriber<>(Integer.MAX_VALUE)
    );
    log.info("main function end");
  }

  public static Mono<Integer> getITems(){
    return Mono.create(monoSink -> {
      monoSink.success(1);
    });
  }
}
