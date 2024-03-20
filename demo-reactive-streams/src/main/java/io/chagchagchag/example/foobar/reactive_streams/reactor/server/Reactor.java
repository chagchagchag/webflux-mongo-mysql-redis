package io.chagchagchag.example.foobar.reactive_streams.reactor.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class Reactor implements Runnable{
  final Selector selector;
  final Executor eventLoopExecutor;
  final ServerSocketChannel serverSocketChannel;

  public Reactor() throws IOException{
    selector = Selector.open();
    eventLoopExecutor = Executors.newSingleThreadExecutor(runnable -> {
      final Thread t = new Thread(runnable);
      t.setName("NioEventLoop");
      return t;
    });

    serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.configureBlocking(false);
    serverSocketChannel
        .register(selector, SelectionKey.OP_ACCEPT)
        .attach(new ServerSocketAcceptor(serverSocketChannel, selector));
  }

  public void bind(final int port) throws IOException {
    serverSocketChannel.bind(new InetSocketAddress(port));
    eventLoopExecutor.execute(this);
  }

  @Override
  public void run() {
    while(true){
      try{
        if(selector.select() == 0) continue;

        final Set<SelectionKey> selectionKeySet = selector.selectedKeys();
        selectionKeySet.forEach(this::dispatch);
        selectionKeySet.clear();
      } catch (Exception e){
        e.printStackTrace();
      }
    }
  }

  private void dispatch(final SelectionKey selectionKey){
    final var handler = (ChannelHandler) selectionKey.attachment();
    try {
      if(selectionKey.isReadable() || selectionKey.isAcceptable()){
        handler.read();
      } else if (selectionKey.isWritable()) {
        handler.write();
      }
    }
    catch (Exception e){
      e.printStackTrace();
    }
  }
}
