package io.chagchagchag.example.foobar.user.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/healthcheck")
public class HealthcheckController {

  @Operation(summary = "healthcheck (/ready)")
  @GetMapping("/ready")
  public Mono<String> ready(){
    return Mono.just("OK");
  }

}
