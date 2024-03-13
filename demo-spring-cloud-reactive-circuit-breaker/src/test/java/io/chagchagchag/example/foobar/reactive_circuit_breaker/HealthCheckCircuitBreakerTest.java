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
import org.springframework.test.context.ContextConfiguration;
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

  @DisplayName("READY_WITH_3000MS_AND_WAIT_1S")
  @Test
  public void TEST_READY_WITH_3000MS_AND_WAIT_1S(){
    // given
    var serviceName = "order-service";
    String fallbackMessage = "FoobarHealthCircuitBreaker Fallback";

    // when, then
    StepVerifier.withVirtualTime(() -> reactiveHealthcheckService.ready(serviceName, 3000L))
        .thenAwait(Duration.ofSeconds(1))
        .expectNext(fallbackMessage)
        .verifyComplete();

    Mockito.verify(ready, never()).ok(serviceName);
  }


}
