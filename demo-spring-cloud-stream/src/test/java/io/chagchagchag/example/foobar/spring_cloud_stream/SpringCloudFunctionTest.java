package io.chagchagchag.example.foobar.spring_cloud_stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.ActiveProfiles;

@Import(TestChannelBinderConfiguration.class)
@ActiveProfiles("stream-function")
@SpringBootTest(classes = SpringCloudStreamApplication.class)
public class SpringCloudFunctionTest {

  @Autowired
  InputDestination inputDestination;

  @Autowired
  OutputDestination outputDestination;

  @Autowired
  ConcurrentHashMap counterMap;

  @BeforeEach
  public void reset(){
    counterMap.clear();
  }

  @DisplayName("COUNTER_STREAM_MESSAGING")
  @Test
  public void TEST_COUNTER_STREAM_MESSAGING(){
    // given
    var ticker = "MSFT";
    var input = new GenericMessage<>(ticker);
    var inputBinding = "increment-in-0";

    // when
    inputDestination.send(input, inputBinding);

    // then
    assertThat(counterMap.getOrDefault(ticker, 0)).isEqualTo(1);
  }

  // Supplier (livenessCheck)

  // Function (stringToUsBigDecimal)

}
