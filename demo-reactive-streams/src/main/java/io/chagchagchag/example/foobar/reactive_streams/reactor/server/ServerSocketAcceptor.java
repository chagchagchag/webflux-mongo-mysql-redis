package io.chagchagchag.example.foobar.reactive_streams.reactor.server;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ServerSocketAcceptor implements ChannelHandler {
  private final ServerSocketChannel serverSocketChannel;
  private final Selector selector;

  @Override
  public void read() throws Exception {
    new EchoHandler(serverSocketChannel.accept(), selector);
  }

  @Override
  public void write() throws Exception {
    throw new UnsupportedOperationException();
  }
}
