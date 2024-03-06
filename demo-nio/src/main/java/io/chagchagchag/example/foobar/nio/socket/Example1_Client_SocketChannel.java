package io.chagchagchag.example.foobar.nio.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Example1_Client_SocketChannel {
  @SneakyThrows
  public static void main(String [] args){
    log.info("main function start");
    try(var socketChannel = SocketChannel.open()){
      var address = new InetSocketAddress("localhost", 8080);
      var connected = socketChannel.connect(address);
      log.info("connected : {}", connected);

      String message = "안녕하세요. 클라이언트에요.";
      ByteBuffer requestMessageBuffer = ByteBuffer.wrap(message.getBytes());
      socketChannel.write(requestMessageBuffer);
      requestMessageBuffer.clear();

      ByteBuffer result = ByteBuffer.allocateDirect(1024);
      while(socketChannel.read(result) > 0){
        result.flip();
        log.info("서버 응답 (Response) = {}", StandardCharsets.UTF_8.decode(result));
        result.clear();
      }
    }
    log.info("main function end");
  }
}
