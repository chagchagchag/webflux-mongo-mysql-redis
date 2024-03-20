package io.chagchagchag.example.foobar.reactive_streams.publisher_subscriber.n_sized_message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscriber;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NSizedMessagePublisher implements Flow.Publisher<Message>{

  @Override
  public void subscribe(Subscriber<? super Message> subscriber) {
    var messages = Collections.synchronizedList(
        new ArrayList<>(
            List.of(
                "1.배고파요",
                "2.편의점가요",
                "3.먹을게 없네요",
                "4.다이어트하자요",
                "5.산책해요",
                "6.잠자요",
                "7.퇴근해요")
        )
    );

    Iterator<String> iterator = messages.iterator();
    var subscription = new NSizedMessageSubscription(subscriber, iterator);
    subscriber.onSubscribe(subscription);
  }
}
