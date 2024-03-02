package io.chagchagchag.example.foobar.sse.application;

public record Event(
    String type,
    String message
) {

}
