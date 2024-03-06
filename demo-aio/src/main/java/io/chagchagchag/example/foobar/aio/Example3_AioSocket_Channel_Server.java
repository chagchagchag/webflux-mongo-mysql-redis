package io.chagchagchag.example.foobar.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Example3_AioSocket_Channel_Server {
  @SneakyThrows
  public static void main(String [] args){
    log.info("main function started");

    try(var serverChannel = AsynchronousServerSocketChannel.open()){
      var address = new InetSocketAddress("localhost", 8080);
      serverChannel.bind(address);

      serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
        @Override
        public void completed(AsynchronousSocketChannel clientSocketChannel, Object attachment) {
          log.info("서버소켓 Accept 시작");
          var requestBuffer = ByteBuffer.allocateDirect(1024);

          clientSocketChannel.read(requestBuffer, null, new CompletionHandler<Integer, Object>() {
            @SneakyThrows
            @Override
            public void completed(Integer result, Object attachment) {
              requestBuffer.flip();
              var requestMessage = StandardCharsets.UTF_8.decode(requestBuffer);
              log.info("requestMessage = {}", requestMessage);

              var response = "안녕하세요. 저는 서버입니다. 이제 서버 끄겠습니다.";
              var responseMessageBuffer = ByteBuffer.wrap(response.getBytes());
              clientSocketChannel.write(responseMessageBuffer);
              clientSocketChannel.close();
              log.info("end client");
            }

            @Override
            public void failed(Throwable exc, Object attachment) {

            }
          });
        }

        @Override
        public void failed(Throwable exc, Object attachment) {

        }
      });

      Thread.sleep(100000); // 100 초 동안 서버 기동
    }
    log.info("main function end");
  }
}
