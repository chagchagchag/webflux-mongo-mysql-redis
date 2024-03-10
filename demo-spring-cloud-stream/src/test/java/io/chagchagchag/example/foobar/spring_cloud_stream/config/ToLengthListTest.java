package io.chagchagchag.example.foobar.spring_cloud_stream.config;

import io.chagchagchag.example.foobar.spring_cloud_stream.SpringCloudStreamApplication;
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
public class ToLengthListTest {
  StreamFunctionsConfig streamFunctionsConfig = new StreamFunctionsConfig();

  @DisplayName("TO_LENGTH_LIST")
  @Test
  public void TEST_TO_LENGTH_LIST(){
    // given
    var strWords = Flux.just("hello", "world", "java");

    // when
    var resultFlux = streamFunctionsConfig.toLengthList().apply(strWords);

    // then
    StepVerifier.create(resultFlux)
        .expectNext("hello".length())
        .expectNext("world".length())
        .expectNext("java".length())
        .verifyComplete();
  }
}
