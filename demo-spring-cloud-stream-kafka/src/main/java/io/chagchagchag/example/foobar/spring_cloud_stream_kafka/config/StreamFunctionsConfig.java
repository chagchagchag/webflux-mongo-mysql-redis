package io.chagchagchag.example.foobar.spring_cloud_stream_kafka.config;

import io.chagchagchag.example.foobar.spring_cloud_stream_kafka.LogComponent;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Configuration
public class StreamFunctionsConfig {
  private final LogComponent logComponent;
  @Bean
  public Supplier<Flux<String>> livenessCheck(){
    return () -> Mono
        .delay(Duration.ofSeconds(10))
        .thenMany(Flux.just("OK"));
  }

  @Bean
  public Function<Flux<String>, Flux<String>> appendCurrTime(){
    return fluxString -> fluxString.handle((str, sink) -> {
      try{
        var currTime = LocalDateTime.now().toString();
        sink.next(String.format("%s ####### %s", currTime, str));
      }
      catch (Exception e){
        e.printStackTrace();
        sink.error(new IllegalStateException("stringToBigDecimal Error"));
      }
    });
  }

  @Bean
  public Consumer<Flux<String>> logLiveness(){
    return strFlux -> strFlux.subscribe(str -> logComponent.info(str));
  }

}
