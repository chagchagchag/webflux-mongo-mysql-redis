package io.chagchagchag.example.foobar.spring_cloud_stream.config;

import io.chagchagchag.example.foobar.spring_cloud_stream.SpringCloudStreamApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Import(TestChannelBinderConfiguration.class)
@ActiveProfiles("stream-function")
@SpringBootTest(classes = SpringCloudStreamApplication.class)
public class StringToBigDecimalTest {
  @Autowired
  InputDestination inputDestination;

  @Autowired
  OutputDestination outputDestination;
}
