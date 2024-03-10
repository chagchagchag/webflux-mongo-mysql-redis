package io.chagchagchag.example.foobar.spring_cloud_stream.config;

import io.chagchagchag.example.foobar.spring_cloud_stream.SpringCloudStreamApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

@Import(TestChannelBinderConfiguration.class)
@ActiveProfiles("stream-function")
@SpringBootTest(classes = SpringCloudStreamApplication.class)
public class LivenessCheckTest {
  StreamFunctionsConfig streamFunctionsConfig = new StreamFunctionsConfig();

  @DisplayName("LIVENESS_CHECK")
  @Test
  public void TEST_LIVENESS_CHECK(){
    // given

    // when
    var livenessCheckFlux = streamFunctionsConfig.livenessCheck().get();

    // then
    StepVerifier.create(livenessCheckFlux)
        .expectNext("OK")
        .verifyComplete();
  }

}
