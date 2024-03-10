package io.chagchagchag.example.foobar.spring_cloud_stream;

import java.util.concurrent.ConcurrentHashMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@Import(TestChannelBinderConfiguration.class)
@ActiveProfiles("stream-function")
@SpringBootTest(classes = SpringCloudStreamApplication.class)
public class NumbersControllerTest {
  @Autowired
  InputDestination inputDestination;

  @Autowired
  OutputDestination outputDestination;

  @Autowired
  ConcurrentHashMap counterMap;

  @Autowired
  WebTestClient webTestClient;

  @DisplayName("TO_LIST_FUNCTION")
  @Test
  public void TEST_TO_LIST_FUNCTION(){
    // given
    var expected = "hello".length();
    var outputBinding = "toLengthList-out-0";

    // when
    webTestClient.get()
        .uri("/numbers/to-list?word="+"hello")
        .exchange()
        .expectStatus().isOk();

    // then
    var result = outputDestination.receive(30, outputBinding);
    String resultMessage = new String(result.getPayload());
    Assertions.assertThat(expected).isEqualTo(Integer.parseInt(resultMessage));
  }

}
