package io.chagchagchag.example.foobar.spring_cloud_stream.config;

import io.chagchagchag.example.foobar.spring_cloud_stream.SpringCloudStreamApplication;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;

@Import(TestChannelBinderConfiguration.class)
@ActiveProfiles("stream-function")
@SpringBootTest(classes = SpringCloudStreamApplication.class)
public class IncrementTest {

  StreamFunctionsConfig streamFunctionsConfig = new StreamFunctionsConfig();

  ConcurrentHashMap<String, Integer> counterMap = new ConcurrentHashMap<>();

  @DisplayName("INCREMENT_TEST")
  @Test
  public void TEST_INCREMENT_TEST(){
    // given
    var tickersFlux = Flux.just("NVDA","SMCI","MSFT", "NVDA");

    // when
    streamFunctionsConfig
        .increment(counterMap)
        .accept(tickersFlux);

    // then
    Assertions.assertEquals(counterMap.get("NVDA"), 2);
    Assertions.assertEquals(counterMap.get("SMCI"), 1);
    Assertions.assertEquals(counterMap.get("MSFT"), 1);
  }

}
