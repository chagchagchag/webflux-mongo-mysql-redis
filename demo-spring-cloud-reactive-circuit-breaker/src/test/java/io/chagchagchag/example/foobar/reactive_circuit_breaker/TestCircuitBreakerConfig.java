package io.chagchagchag.example.foobar.reactive_circuit_breaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;

@Slf4j
@TestConfiguration
public class TestCircuitBreakerConfig {
  @Bean
  public Customizer<ReactiveResilience4JCircuitBreakerFactory> healthCheck(){
    var circuitBreakerConfig = CircuitBreakerConfig.custom()
        .slidingWindowSize(10)
        .failureRateThreshold(75)
        .enableAutomaticTransitionFromOpenToHalfOpen()
        .waitDurationInOpenState(Duration.ofSeconds(5))
        .permittedNumberOfCallsInHalfOpenState(6)
        .ignoreExceptions(ArithmeticException.class)
        .maxWaitDurationInHalfOpenState(Duration.ofSeconds(30))
        .build();

    var timeLimiterConfig = TimeLimiterConfig.custom()
        .cancelRunningFuture(true)
        .timeoutDuration(Duration.ofSeconds(3))
        .build();

    var circuitBreakerId = "healthCheck";

    return factory -> {
      factory.addCircuitBreakerCustomizer(loggingCustomizer(), circuitBreakerId);
      factory.configure(builder -> {
        builder
            .circuitBreakerConfig(circuitBreakerConfig)
            .timeLimiterConfig(timeLimiterConfig);
      }, circuitBreakerId);
    };
  }

  private Customizer<CircuitBreaker> loggingCustomizer(){
    return Customizer.once(circuitBreaker -> {
      var cbName = circuitBreaker.getName();
      circuitBreaker.getEventPublisher()
          .onSuccess(event -> log.info("circuit breaker ({}) success", cbName))
          .onError(event -> log.info("circuit breaker ({}) error ===> {}", cbName, event.getThrowable().toString()))
          .onStateTransition(event -> {
            log.info("circuit breaker ({}) changed from {} to {}",
                cbName,
                event.getStateTransition().getFromState(),
                event.getStateTransition().getToState());
          })
          .onSlowCallRateExceeded(event ->
              log.info("circuit breaker ({}) slow call rate exceeded ===> {}",
                  cbName, event.getSlowCallRate()))
          .onFailureRateExceeded(event ->
              log.info("circuit breaker ({}) failure rate exceeded ===> {}",
                  cbName, event.getFailureRate()));

    }, CircuitBreaker::getName);
  }

  @Bean
  public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer(){
    return factory -> {
      factory.configureDefault(id -> {
        factory.addCircuitBreakerCustomizer(loggingCustomizer(), id);
        return new Resilience4JConfigBuilder(id)
            .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
            .build();
      });
    };
  }

  @Bean
  public Customizer<ReactiveResilience4JCircuitBreakerFactory> tinyCustomizer(){
    var cbConfig = CircuitBreakerConfig.custom()
        .failureRateThreshold(50)
        .slidingWindowSize(3)
        .build();

    var targets = new String[]{"tiny"};
    return factory -> {
      factory.addCircuitBreakerCustomizer(loggingCustomizer(), targets);
      factory.configure(
          builder -> builder.circuitBreakerConfig(cbConfig),
          targets
      );
    };
  }

  @Bean
  public Customizer<ReactiveResilience4JCircuitBreakerFactory> autoHalf(){
    var cbConfig = CircuitBreakerConfig.custom()
        .failureRateThreshold(50)
        .slidingWindowSize(3)
        .enableAutomaticTransitionFromOpenToHalfOpen() // auto half 설정
        .waitDurationInOpenState(Duration.ofSeconds(5))
        .build();

    var targets = new String[]{"autoHalf"};
    return factory -> {
      factory.addCircuitBreakerCustomizer(
          loggingCustomizer(), targets);
      factory.configure(builder -> {
        builder.circuitBreakerConfig(cbConfig);
      }, targets);
    };
  }

  @Bean
  public Customizer<ReactiveResilience4JCircuitBreakerFactory> halfOpen() {
    var cbConfig = CircuitBreakerConfig.custom()
        .failureRateThreshold(50)
        .slidingWindowSize(4)
        .enableAutomaticTransitionFromOpenToHalfOpen()
        .waitDurationInOpenState(Duration.ofSeconds(3))
        .permittedNumberOfCallsInHalfOpenState(6) //
        .build();

    var targets = new String[]{"halfOpen"};

    return factory -> {
      factory.addCircuitBreakerCustomizer(loggingCustomizer(), targets);
      factory.configure(
          builder -> builder.circuitBreakerConfig(cbConfig),
          targets
      );
    };
  }
}
