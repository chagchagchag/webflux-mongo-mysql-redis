package io.chagchagchag.example.foobar.reactive_streams.reactor.server;

import java.io.IOException;

public class EchoServerMain {

  public static void main(String[] args) throws IOException {
    var reactor = new Reactor();
    reactor.bind(8080);
  }
}
