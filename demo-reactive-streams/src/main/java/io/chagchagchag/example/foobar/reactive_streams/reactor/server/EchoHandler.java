package io.chagchagchag.example.foobar.reactive_streams.reactor.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoHandler implements ChannelHandler{
  static final String POISON_PILL = "BYE";
  final SocketChannel socketChannel;
  final Selector selector;
  final LinkedList<String> msgQueue;
  final MsgCodec msgCodec;

  public EchoHandler(
      final SocketChannel socketChannel,
      final Selector selector
  ) throws IOException {
    this.socketChannel = socketChannel;
    this.selector = selector;
    this.msgQueue = new LinkedList<>();
    this.msgCodec = new MsgCodec();

    socketChannel.configureBlocking(false);
    socketChannel.register(selector, SelectionKey.OP_READ).attach(this);

    selector.wakeup();
  }

  @Override
  public void read() throws Exception {
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    socketChannel.read(buffer);
    final var msg = msgCodec.decode(buffer);

    log.info("<=== " + msg);
    msgQueue.addLast(msg);

    socketChannel
        .register(selector, SelectionKey.OP_WRITE)
        .attach(this);

  }

  @Override
  public void write() throws Exception {
    final var msg = msgQueue.removeFirst();
    socketChannel.write(msgCodec.encode(msg));

    log.info("===> " + msg);

    if(POISON_PILL.equals(msg.trim())){
      log.info("Closing " + socketChannel);
      socketChannel.close();
      return;
    }

    socketChannel.register(selector, SelectionKey.OP_READ).attach(this);
    selector.wakeup();
  }
}
