package io.chagchagchag.example.foobar.reactive_streams.publisher_subscriber.fixed_int;

import java.util.concurrent.Flow;
import lombok.SneakyThrows;

public class FixedIntPublisherClient {
  @SneakyThrows
  public static void main(String [] args){
    Flow.Publisher publisher = new FixedIntPublisher();
    Flow.Subscriber subscriber = new RequestNSubscriber<>(3);

    publisher.subscribe(subscriber);
    Thread.sleep(1000);
  }
}
