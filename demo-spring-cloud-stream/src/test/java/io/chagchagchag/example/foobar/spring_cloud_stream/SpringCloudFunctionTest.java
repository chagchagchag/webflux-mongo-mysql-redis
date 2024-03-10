package io.chagchagchag.example.foobar.spring_cloud_stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
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
  @DisplayName("LIVENESS_CHECK_STREAM_MESSAGING")
  @Test
  public void TEST_LIVENESS_CHECK_STREAM_MESSAGING(){
    // given
    var outputBinding = "livenessCheck-out-0";
    var expectedMsg = List.of("OK");

    for(var name: expectedMsg){
      // when
      var received = outputDestination.receive(300, outputBinding);
      String outputMessage = new String(received.getPayload());

      // then
      assertThat(outputMessage.equals(name)).isTrue();
    }
  }


  // Function (stringToBigDecimal)
  @DisplayName("STRING_TO_BIG_DECIMAL")
  @Test
  public void TEST_STRING_TO_BIG_DECIMAL() throws ParseException {
    // given
    var inputBinding = "stringToBigDecimal-in-0";
    var outputBinding = "stringToBigDecimal-out-0";
    var input = new GenericMessage<>("28.39");
    var expected = BigDecimal.valueOf(28.39);

    // when
    // 먼저 값을 보낸다.
    inputDestination.send(input, inputBinding);

    // then
    // 치리되어 반환하는 값을 받는다.
    var received = outputDestination.receive(30, outputBinding);
    var receivedStr = new String(received.getPayload());
    var receivedDecimal = new BigDecimal(receivedStr);
    assertThat(receivedDecimal).isEqualTo(expected);
  }

}
