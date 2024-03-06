package io.chagchagchag.example.foobar.aio;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Example1_AioFileChannel {
  @SneakyThrows
  public static void main(String [] args){
    log.info("main function started");

    var filePath = Example1_AioFileChannel.class
        .getClassLoader()
        .getResource("example1-data.txt")
        .getFile();

    var file = new File(filePath);

    try(var channel = AsynchronousFileChannel.open(file.toPath())){
      ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
      channel.read(buffer, 0, null, new CompletionHandler<Integer, Object>() {
        @SneakyThrows
        @Override
        public void completed(Integer result, Object attachment) {
          buffer.flip();
          var resultString = StandardCharsets.UTF_8.decode(buffer);
          log.info(String.format("resultString = %s", resultString));
          channel.close();
        }

        @Override
        public void failed(Throwable exc, Object attachment) {

        }

      });

      while (channel.isOpen()){
        log.info("Reading ... ");
      }
    }

    log.info("main function end");
  }
}
