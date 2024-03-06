package io.chagchagchag.example.foobar.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Example3_AioSocket_Channel_Client {
  @SneakyThrows
  public static void main(String [] args){
    List<CompletableFuture> completableFutures = new ArrayList<>();
    log.info("main function started");

    var executor = Executors.newFixedThreadPool(30);

    for(var i=0; i<100; i++){
      log.info("sending start");
      var future = CompletableFuture.runAsync(()->{
        SocketChannel socketChannel = null;
        try {
          socketChannel = SocketChannel.open();
          var address = new InetSocketAddress("localhost", 8080);
          var connected = socketChannel.connect(address);
          log.info("connected >> {}", connected);

          String request = "저는 클라이언트에요";
          ByteBuffer requestBuffer = ByteBuffer.wrap(request.getBytes());
          socketChannel.write(requestBuffer);

          ByteBuffer result = ByteBuffer.allocate(1024);
          if (!socketChannel.isConnected())
            return;

          while (socketChannel.isConnected() && socketChannel.read(result) > 0) {
            result.flip();
            log.info("서버에서 응답이 왔어요. 응답 메시지 >>> {}", StandardCharsets.UTF_8.decode(result));
            result.clear();
          }
        }
        catch (Exception e){
          e.printStackTrace();
          log.info("서버가 중지 되었습니다.");
        }
        finally {
          try {
            socketChannel.close();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }
        log.info("sending end");
      }, executor);

      completableFutures.add(future);
    }

    CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();
    executor.shutdown();
    log.info("main function end");
  }
}
