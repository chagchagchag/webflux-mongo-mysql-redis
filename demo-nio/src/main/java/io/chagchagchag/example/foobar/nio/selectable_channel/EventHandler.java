package io.chagchagchag.example.foobar.nio.selectable_channel;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventHandler {
  @RequiredArgsConstructor
  class EventKey{
    private final SelectionKey selectionKey;
    @Getter
    private final EventHandler eventHandler;
    public SocketChannel channel(){
      return (SocketChannel) selectionKey.channel();
    }
  }

  private final Selector selector;
  private Consumer<EventKey> connectConsumer;
  private Consumer<EventKey> readConsumer;

  public EventHandler() throws IOException {
    this.selector = Selector.open();
  }

  public void close() throws IOException {
    this.selector.close();
  }

  public void registerConnect(
      SocketChannel socketChannel, Consumer<EventKey> consumer
  ) throws IOException {
    socketChannel.register(selector, SelectionKey.OP_CONNECT);
    connectConsumer = consumer;
  }

  public void registerRead(
      SocketChannel socketChannel, Consumer<EventKey> consumer
  ) throws IOException {
    socketChannel.register(selector, SelectionKey.OP_READ);
    readConsumer = consumer;
  }

  public void listen(Runnable onFinish) throws InterruptedException {
    log.info("== listen start");

    var executor = Executors.newSingleThreadExecutor();
    executor.submit(() -> {
      try{
        selectableLoop:
        while(true){
          selector.select();
          var selectionKeys = selector.selectedKeys();
          var iterator = selectionKeys.iterator();

          while (iterator.hasNext()){
            var selectionKey = iterator.next();
            var eventKey = new EventKey(selectionKey, this);
            iterator.remove();

            if(selectionKey.isConnectable()){
              connectConsumer.accept(eventKey);
            }
            else if(selectionKey.isReadable()){
              readConsumer.accept(eventKey);
              break selectableLoop;
            }
          }
        }
        onFinish.run();
        this.close();
      }
      catch (Exception e){
        log.error("Error => {}", e.getMessage());
      }
    });

    executor.shutdown();
    log.info("== listen end");
  }
}
