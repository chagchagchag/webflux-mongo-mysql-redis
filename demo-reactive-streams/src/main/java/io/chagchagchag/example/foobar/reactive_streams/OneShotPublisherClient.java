package io.chagchagchag.example.foobar.reactive_streams;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OneShotPublisherClient {
  @SneakyThrows
  public static void main(String [] args){
    var publisher = new OneShotPublisher();
    publisher.subscribe(new LogSubscriber<>());
    Thread.sleep(100);
  }
}
