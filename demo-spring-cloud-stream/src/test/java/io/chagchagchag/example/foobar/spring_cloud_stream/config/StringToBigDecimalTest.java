package io.chagchagchag.example.foobar.spring_cloud_stream.config;

import io.chagchagchag.example.foobar.spring_cloud_stream.SpringCloudStreamApplication;
import java.math.BigDecimal;
import java.util.function.Predicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Import(TestChannelBinderConfiguration.class)
@ActiveProfiles("stream-function")
@SpringBootTest(classes = SpringCloudStreamApplication.class)
public class StringToBigDecimalTest {
  StreamFunctionsConfig streamFunctionsConfig = new StreamFunctionsConfig();

  @DisplayName("STRING_TO_BIG_DECIMAL")
  @Test
  public void TEST_STRING_TO_BIG_DECIMAL(){
    // given
    var strNumbers = Flux.just("28.39");
    var expected = BigDecimal.valueOf(28.39);

    Predicate<BigDecimal> equals = d -> {
      if(d.equals(expected)) return true;
      else return false;
    };

    // when
    var bigDeciamlFlux = streamFunctionsConfig.stringToBigDecimal().apply(strNumbers);

    // then
    StepVerifier.create(bigDeciamlFlux)
        .expectNextMatches(equals)
        .verifyComplete();
  }

}
