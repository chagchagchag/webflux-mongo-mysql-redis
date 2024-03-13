package io.chagchagchag.example.foobar.reactive_circuit_breaker;

import io.chagchagchag.example.foobar.reactive_circuit_breaker.common.Ready;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReactiveHealthcheckService {
  private final ReactiveCircuitBreakerFactory healthCheckCircuitBreaker;
  private final Ready ready;

  private final String fallbackMessage = "FoobarHealthCircuitBreaker Fallback";

  /**
   * health 체크를 위한 "OK (ServiceName)" 문자열을 Mono 기반의 동시성 코드로 생성하는 공통 코드입니다.
   * @param from SERVICE 명
   * @param delayMs 지연할 시간
   * @return
   */
  public Mono<String> delayedOk(String from, Long delayMs){
    var duration = Duration.ofMillis(delayMs);
    return Mono.delay(duration)
        .then()
        .then(Mono.fromCallable(() -> ready.ok(from)));
  }

  /**
   * 다른 설정이 되지 않은 기본설정이 되어 있는 circuitBreaker 를 기반으로 ready() 함수에 대해 회로차단기를 걸어두었습니다.
   * "OK" 신호를 내보내는 데에 delayMs 로 들어온 지연 시간이 걸리도록 합니다.
   * 이때 CircuitBreaker 내의 허용된 지연시간, 실패 횟수, 슬라이딩윈도우, 실패율 등에 따라
   * CircuitBreaker 가 이 기능을 차단할지, 차단하지 않을지를 결정하게 됩니다.
   * @param from SERVICE 명
   * @param delayMs 지연할 시간
   * @return
   */
  public Mono<String> ready(String from, Long delayMs){
    return delayedOk(from, delayMs)
        .transform(it -> {
          var cb = healthCheckCircuitBreaker.create("normal");
          return cb.run(it, throwable -> Mono.just(fallbackMessage));
        });
  }

  /**
   * "OK"신호를 내보낼 때 exception 이 발생하는 경우를 가정합니다.
   * @param from SERVICE 명
   * @return
   */
  public Mono<String> readyWithException(String from){
    Mono<String> mono = Mono.error(new RuntimeException("Err"));

    return mono.transform(it -> {
      var circuitBreaker = healthCheckCircuitBreaker.create("exception");
      return circuitBreaker
          .run(it, throwable -> Mono.just(fallbackMessage));
    });
  }

  /**
   * "OK" 신호를 내보내는 기능을 특정 ID를 가진 서킷브레이커로 감쌉니다.
   * @param id
   * @param from SERVICE 명
   * @param delayMs 지연할 시간
   * @return
   */
  public Mono<String> readyWithId(String id, String from, Long delayMs){
    return delayedOk(from, delayMs)
        .transform(it -> {
          var cb = healthCheckCircuitBreaker.create(id);
          return cb.run(it, throwable -> Mono.just(fallbackMessage));
        });
  }

  public Mono<String> readyWithIdAndGroup(
      String id, String group, String from, Long delayMs
  ){
    return delayedOk(from, delayMs)
        .transform(it -> {
          var cb = healthCheckCircuitBreaker.create(id, group);
          return cb.run(it, throwable -> Mono.just(fallbackMessage));
        });
  }
}
