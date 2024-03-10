package io.chagchagchag.example.foobar.spring_cloud_stream_kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogComponent {
  public void info(String msg){
    log.info(msg);
  }
}
