package io.chagchagchag.example.foobar.reactive_streams;

import java.util.concurrent.Flow;
import lombok.SneakyThrows;

public class FixedIntPublisherClient {
  @SneakyThrows
  public static void main(String [] args){
    Flow.Publisher publisher = new FixedIntPublisher();
    Flow.Subscriber subscriber = new RequestNSubscriber<>(Integer.MAX_VALUE);

    publisher.subscribe(subscriber);
    Thread.sleep(100);
  }
}
