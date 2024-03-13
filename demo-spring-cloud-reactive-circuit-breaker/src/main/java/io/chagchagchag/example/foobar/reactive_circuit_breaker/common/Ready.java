package io.chagchagchag.example.foobar.reactive_circuit_breaker.common;

import org.springframework.stereotype.Component;

@Component
public class Ready {
  public String ok (String serviceName){
    return String.format("OK (%s)", serviceName);
  }


}
