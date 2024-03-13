package io.chagchagchag.example.foobar.reactive_circuit_breaker.common;

import java.time.Duration;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class Ready {
  public String ok (String serviceName){
    return String.format("(at : %s) Ready OK", serviceName);
  }

  public Mono<String> delayedOk(String from, Long delayMs){
    var duration = Duration.ofMillis(delayMs);
    return Mono.delay(duration)
        .then()
        .then(Mono.fromCallable(() -> ok(from)));
  }
}
