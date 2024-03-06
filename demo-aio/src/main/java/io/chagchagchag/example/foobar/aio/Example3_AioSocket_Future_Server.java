package io.chagchagchag.example.foobar.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Example3_AioSocket_Future_Server {
  @SneakyThrows
  public static void main(String [] args){
    log.info("main function started");

    var serverSocketChannel = AsynchronousServerSocketChannel.open();
    var address = new InetSocketAddress("localhost", 8080);
    serverSocketChannel.bind(address);

    // client 접속 받는 Future
    Future<AsynchronousSocketChannel> clientSocketFuture = serverSocketChannel.accept();

    // 조금 더 갖춰진 예제를 만든다면 별도의 스레드에서 실행하도록 작성 필요
    while(!clientSocketFuture.isDone()){
      Thread.sleep(200);
      log.info("Client Socket is still live (ON)");
    }

    var clientSocket = clientSocketFuture.get();

    var requestBuffer = ByteBuffer.allocateDirect(1024);
    Future<Integer> readFuture = clientSocket.read(requestBuffer);
    while(!readFuture.isDone()){
      log.info("(Still Reading... ) Reading Future is Not Closed ... ");
    }

    requestBuffer.flip();
    var request = StandardCharsets.UTF_8.decode(requestBuffer);
    log.info("클라이언트의 메시지 >>> {}", request);

    var response = "서버입니다. 안녕히 잘 지내시죠? 이제 서버 끕니다.";
    var responseMessageBuffer = ByteBuffer.wrap(response.getBytes());
    clientSocket.write(responseMessageBuffer);
    clientSocket.close();

    log.info("main function end");
  }
}
