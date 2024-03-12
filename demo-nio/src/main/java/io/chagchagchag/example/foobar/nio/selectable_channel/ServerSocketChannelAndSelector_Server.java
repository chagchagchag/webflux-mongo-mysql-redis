package io.chagchagchag.example.foobar.nio.selectable_channel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerSocketChannelAndSelector_Server {
  @SneakyThrows
  public static void main(String[] args) {
    log.info("main function started");

    try(var serverChannel = ServerSocketChannel.open()){
      // selector 생성
      var selector = Selector.open();

      var address = new InetSocketAddress("localhost", 8080);
      serverChannel.bind(address);

      // serverChannel 을 nonblocking 으로 세팅
      serverChannel.configureBlocking(false);
      serverChannel.register(selector, SelectionKey.OP_ACCEPT);

      while(true){
        selector.select(); // blocking (준비될 때 까지)
        var selectedKeys = selector.selectedKeys();
        for(var key : selectedKeys){
          if(!key.isValid()) return;
          if(key.isAcceptable()){ // ACCEPT 이벤트일 경우
            // accept 를 통해 ClientSocket 획득
            var clientSocket = ((ServerSocketChannel) key.channel()).accept();
            // clientSocket 을 non-blocking 으로 설정
            clientSocket.configureBlocking(false);
            // clientSocket 을 selector 에 등록
            clientSocket.register(selector, SelectionKey.OP_READ);
          }
          if(key.isReadable()){ // READ 이벤트 일 때
            // clientSocket 을 얻어옴
            var clientSocket = (SocketChannel) key.channel();

            var requestBuffer = ByteBuffer.allocate(1024);
            clientSocket.read(requestBuffer); // clientSocket 으로부터 데이터 Read
            requestBuffer.flip();

            var received = new String(requestBuffer.array()).trim();
            log.info("client received = {}", received);

            var send = "답장보냅니다.";
            var responseBuffer = ByteBuffer.wrap(send.getBytes());
            clientSocket.write(responseBuffer);
            responseBuffer.clear();
            clientSocket.close();
          }
        }

        selectedKeys.clear();
      }
    }

//    log.info("main function end");
  }
}
