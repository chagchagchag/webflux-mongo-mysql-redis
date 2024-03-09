package io.chagchagchag.example.foobar.spring_cloud_stream.config;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

@Slf4j
@Configuration
public class StreamFunctionsConfig {
  @Bean
  public ConcurrentHashMap<String, Integer> counterMap(){
    return new ConcurrentHashMap<>();
  }

  @Bean
  public Consumer<Flux<String>> increment(ConcurrentHashMap<String, Integer> counterMap){
    return fluxKey -> {
      fluxKey.subscribe(key -> {
        counterMap.computeIfPresent(key, (k, v) ->v+1);
        counterMap.computeIfAbsent(key, v -> 1);
      });
    };
  }

  @Bean
  public Supplier<Flux<String>> livenessCheck(){
    return () -> Flux.just("OK");
  }

  @Bean
  public Function<Flux<String>, Flux<BigDecimal>> stringToBigDecimal(){
    return fluxString -> fluxString.handle((str, sink) -> {
      try{
        Number parse = NumberFormat.getNumberInstance(Locale.US).parse(str);
        sink.next(new BigDecimal(parse.toString()));
      }
      catch (ParseException e){
        e.printStackTrace();
        sink.error(new IllegalStateException("Number Format is not supported."));
      }
    });
  }

}
