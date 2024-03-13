package io.chagchagchag.example.foobar.reactive_circuitbreaker.healthcheck;

import io.chagchagchag.example.foobar.reactive_circuitbreaker.common.Ready;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReadyHealthcheckCBService {
  private final ReactiveCircuitBreakerFactory healthCheckCircuitBreaker;
  private final Ready ready;
  private final String fallbackMessage = "FoobarHealthCircuitBreaker Fallback";

  public Mono<String> ready(String from, Long delayMs){
    var duration = Duration.ofMillis(delayMs);
    return Mono.delay(duration)
        .then()
        .then(Mono.fromCallable(() -> ready.ok(from)))
        .transform(it -> {
          var cb = healthCheckCircuitBreaker.create("normal");
          return cb.run(it, throwable -> Mono.just(fallbackMessage));
        });
  }

  public Mono<String> readyWithException(String from){
    Mono<String> mono = Mono.error(new RuntimeException("Err"));

    return mono.transform(it -> {
      var circuitBreaker = healthCheckCircuitBreaker.create("exception");
      return circuitBreaker
          .run(it, throwable -> Mono.just(fallbackMessage));
    });
  }

  public Mono<String> readyWithId(String id, String from, Long delayMs){
    return ready.delayedOk(from, delayMs)
        .transform(it -> {
          var cb = healthCheckCircuitBreaker.create(id);
          return cb.run(it, throwable -> Mono.just(fallbackMessage));
        });
  }

  public Mono<String> readyWithIdAndGroup(
      String id, String group, String from, Long delayMs
  ){
    return ready.delayedOk(from, delayMs)
        .transform(it -> {
          var cb = healthCheckCircuitBreaker.create(id, group);
          return cb.run(it, throwable -> Mono.just(fallbackMessage));
        });
  }
}
