package io.chagchagchag.example.foobar.nio.selectable_channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SocketChannelNonBlockingEventHandler_Client {
  @SneakyThrows
  public static void main(String[] args) {
    log.info("main function start");

    var address = new InetSocketAddress("localhost", 3000);

    var eventHandler = new EventHandler();
    var socketChannel = SocketChannel.open();
    socketChannel.configureBlocking(false);
    socketChannel.connect(address);

    eventHandler.registerConnect(socketChannel, eventKey -> handleConnect(eventKey));
    eventHandler.listen(() -> {
      try{
        socketChannel.close();
      }
      catch (IOException e){
        throw new RuntimeException(e);
      }
    });
    log.info("main function end");
  }

  public static void handleConnect(EventHandler.EventKey eventKey){
    try{
      var channel = eventKey.channel();
      if(channel.isConnectionPending()){
        channel.finishConnect();
      }

      String request = "GET / HTTP/1.1\r\nHost: localhost:8080\r\n\r\n";
      ByteBuffer reqBuffer = ByteBuffer.wrap(request.getBytes());
      channel.write(reqBuffer);

      eventKey.getEventHandler().registerRead(channel, key -> handleRead(key));
    }
    catch (IOException e){
      throw new RuntimeException(e);
    }
  }

  public static void handleRead(EventHandler.EventKey eventKey){
    try{
      var channel = eventKey.channel();
      ByteBuffer res = ByteBuffer.allocate(1024);
      while(channel.read(res) > 0){
        res.flip();
        log.info("response :: {}", StandardCharsets.UTF_8.decode(res));
        res.clear();
      }
    }
    catch (IOException e){
      throw new RuntimeException(e);
    }
  }
}
