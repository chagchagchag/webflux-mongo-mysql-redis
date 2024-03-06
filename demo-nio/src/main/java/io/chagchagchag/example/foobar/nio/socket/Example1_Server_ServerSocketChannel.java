package io.chagchagchag.example.foobar.nio.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Example1_Server_ServerSocketChannel {
  @SneakyThrows
  public static void main (String [] args){
    log.info("main function started");
    try(var serverChannel = ServerSocketChannel.open()){
      var address = new InetSocketAddress("localhost", 8080);
      serverChannel.bind(address);

      try(var clientSocket = serverChannel.accept()){
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        clientSocket.read(buffer);
        buffer.flip();

        var decodeMessage = StandardCharsets.UTF_8.decode(buffer);
        var clientMessage = String.valueOf(decodeMessage);
        log.info("클라이언트로부터 온 메시지 = {}", clientMessage);

        var responseMessage = "안녕하세요. 저는 서버입니다.";
        var responseBuffer = ByteBuffer.wrap(responseMessage.getBytes());
        clientSocket.write(responseBuffer);
        responseBuffer.flip();
      }
    }
    log.info("main function end");
  }
}
