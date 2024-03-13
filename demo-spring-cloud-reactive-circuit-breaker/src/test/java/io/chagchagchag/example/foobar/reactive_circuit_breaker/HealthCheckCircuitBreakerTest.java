package io.chagchagchag.example.foobar.reactive_circuit_breaker;


import static org.mockito.Mockito.never;

import io.chagchagchag.example.foobar.reactive_circuit_breaker.common.Ready;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.springboot3.circuitbreaker.autoconfigure.CircuitBreakerAutoConfiguration;
import io.github.resilience4j.springboot3.timelimiter.autoconfigure.TimeLimiterAutoConfiguration;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JAutoConfiguration;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JAutoConfiguration;
import org.springframework.context.annotation.Import;
import reactor.test.StepVerifier;

@Slf4j
@Import(TestCircuitBreakerConfig.class)
@ImportAutoConfiguration(
    classes = {
        ReactiveResilience4JAutoConfiguration.class,
        Resilience4JAutoConfiguration.class,
        CircuitBreakerAutoConfiguration.class,
        TimeLimiterAutoConfiguration.class
    }
)
@SpringBootTest
public class HealthCheckCircuitBreakerTest {
  @Autowired
  private ReactiveHealthcheckService reactiveHealthcheckService;
  @Autowired
  private CircuitBreakerRegistry circuitBreakerRegistry;
  @SpyBean
  private Ready ready;

  String fallbackMessage = "FoobarHealthCircuitBreaker Fallback";

  @BeforeEach
  public void reset(){
    circuitBreakerRegistry.getAllCircuitBreakers()
        .forEach(circuitBreaker -> circuitBreaker.reset());
  }

  @Test
  public void contextLoads(){

  }

  @DisplayName("READY_WITHOUT_DELAY")
  @Test
  public void TEST_READY_WITHOUT_DELAY(){
    // given
    var serviceName = "order-service";
    var expectedMessage = String.format("OK (%s)", serviceName);

    // when
    var mono = reactiveHealthcheckService.ready(serviceName, 0L);

    // then
    StepVerifier.create(mono)
        .expectNext(expectedMessage)
        .verifyComplete();

    Mockito.verify(ready).ok(serviceName);
  }

  // ready() 메서드의 실행 시간이 test 설정에 해둔 5초를 넘어가지 않을 경우 정상
  @DisplayName("READY_WITH_3000MS_AND_WAIT_1S")
  @Test
  public void TEST_READY_WITH_3000MS_AND_WAIT_1S(){
    // given
    String serviceName = "order-service";
    String successMessage = String.format("OK (%s)", serviceName);

    // when, then
    StepVerifier.withVirtualTime(() -> reactiveHealthcheckService.ready(serviceName, 3000L))
        .thenAwait(Duration.ofSeconds(1))
        .expectNext(fallbackMessage)
        .verifyComplete();

    Mockito.verify(ready, never()).ok(successMessage);
  }

  // ready() 메서드의 실행 시간이 test 설정에 해둔 5초를 넘어가지 않을 경우 정상
  @DisplayName("READY_WITH_3000MS_AND_WAIT_5S")
  @Test
  public void TEST_READY_WITH_3000MS_AND_WAIT_5S(){
    // given
    String serviceName = "order-service";
    String successMessage = String.format("OK (%s)", serviceName);

    // when, then
    StepVerifier.withVirtualTime(() -> reactiveHealthcheckService.ready(serviceName, 3000L))
        .thenAwait(Duration.ofSeconds(5))
        .expectNext(fallbackMessage)
        .verifyComplete();

    Mockito.verify(ready, Mockito.never()).ok(successMessage);
  }

  // 예외 발생시 fallbackMessage
  @DisplayName("READY_WITH_THROWING_EXCEPTION")
  @Test
  public void TEST_READY_WITH_THROWING_EXCEPTION(){
    // given
    var serviceName = "order-service";
    var expectedMessage = fallbackMessage;

    // when, then
    StepVerifier.create(reactiveHealthcheckService.readyWithException(serviceName))
        .expectNext(fallbackMessage)
        .verifyComplete();
  }


  // tiny 회로차단기 ON 되는 테스트 케이스
  @DisplayName("READY_METHOD_MAKE_CB_STATE_OPEN")
  @Test
  public void TEST_READY_METHOD_MAKE_CB_STATE_OPEN(){
    // given
    String serviceName = "order-service";
    String successMessage = String.format("OK (%s)", serviceName);
    String expectedMessage = fallbackMessage;

    // when (delay 없는 call 은 성공, 3개의 call 실행, Sliding Window 채우기 위한 용도)
    for(int i=0; i<3; i++){
      StepVerifier.create(reactiveHealthcheckService.readyWithId("tiny", serviceName, 0L))
          .expectNext(successMessage)
          .verifyComplete();
    }

    // then (5s delay call 을 2번 실행 -> 50% 실패 -> circuit breaker closed)
    for(int i=0; i<2; i++){
      StepVerifier
          .withVirtualTime(()->reactiveHealthcheckService.readyWithId("tiny", serviceName, 7000L))
          .thenAwait(Duration.ofSeconds(2))
          .expectNext(expectedMessage)
          .verifyComplete();
    }

    for(int i=0; i<100; i++){
      StepVerifier
          .create(reactiveHealthcheckService.readyWithId("tiny", serviceName, 0L))
          .expectNext(expectedMessage)
          .verifyComplete();
    }

    Mockito.verify(ready, Mockito.times(3)).ok(serviceName);
  }



}
