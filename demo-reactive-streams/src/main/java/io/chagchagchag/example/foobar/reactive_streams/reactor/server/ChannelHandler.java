package io.chagchagchag.example.foobar.reactive_streams.reactor.server;

public interface ChannelHandler {
  void read() throws Exception;
  void write() throws Exception;
}
