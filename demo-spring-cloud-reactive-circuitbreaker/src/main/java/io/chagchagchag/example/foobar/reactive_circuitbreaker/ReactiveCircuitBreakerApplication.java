package io.chagchagchag.example.foobar.reactive_circuitbreaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
//    exclude = {
//        CircuitBreakerAutoConfiguration.class,
//        CircuitBreakerStreamEventsAutoConfiguration.class,
//        Resilience4JAutoConfiguration.class,
//        ReactiveResilience4JAutoConfiguration.class
//    }
)
public class ReactiveCircuitBreakerApplication {
  public static void main(String[] args) {
    SpringApplication.run(ReactiveCircuitBreakerApplication.class, args);
  }
}
