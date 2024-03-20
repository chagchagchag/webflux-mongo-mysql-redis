package io.chagchagchag.example.foobar.reactive_streams.publisher_subscriber.n_sized_message;

import java.util.concurrent.Flow;
import lombok.SneakyThrows;

public class NSizedMessagePubSubExample {
  @SneakyThrows
  public static void main(String[] args) {
    Flow.Publisher<Message> publisher = new NSizedMessagePublisher();
    Flow.Subscriber<Message> subscriber = new NSizedMessageSubscriber<>(5);
    publisher.subscribe(subscriber);
    Thread.sleep(1000);
  }
}
