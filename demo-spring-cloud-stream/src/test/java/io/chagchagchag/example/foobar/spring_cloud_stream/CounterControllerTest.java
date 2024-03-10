package io.chagchagchag.example.foobar.spring_cloud_stream;

import java.util.concurrent.ConcurrentHashMap;
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
public class CounterControllerTest {
  @Autowired
  InputDestination inputDestination;

  @Autowired
  OutputDestination outputDestination;

  @Autowired
  ConcurrentHashMap counterMap;

  @Autowired
  WebTestClient webTestClient;

  @DisplayName("STREAM_BRIDGE_WEBCLIENT")
  @Test
  public void TEST_STREAM_BRIDGE_WEBCLIENT(){
    // given
    var ticker = "MSFT";

    // when
    webTestClient.get()
        .uri("/increment?ticker="+ "MSFT")
        .exchange()
        .expectStatus().isOk();

    // then

  }

}
