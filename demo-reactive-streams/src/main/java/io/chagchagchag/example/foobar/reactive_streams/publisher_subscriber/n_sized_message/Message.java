package io.chagchagchag.example.foobar.reactive_streams.publisher_subscriber.n_sized_message;

public record Message(
    String message,
    Integer requestedCnt
) {

}
