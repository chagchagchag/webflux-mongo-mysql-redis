package io.chagchagchag.example.foobar.aio;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Example2_AioFileFuture {
  @SneakyThrows
  public static void main(String [] args){
    log.info("main function started");

    var filePath = Example1_AioFileChannel.class
        .getClassLoader()
        .getResource("example2-data.txt")
        .getFile();

    var file = new File(filePath);

    try(var channel = AsynchronousFileChannel.open(file.toPath())){
      ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
      Future<Integer> readFuture = channel.read(buffer, 0);

      while (!readFuture.isDone()){
        log.info("Reading ... ");
      }
      buffer.flip();
      var resultString = StandardCharsets.UTF_8.decode(buffer);
      log.info(String.format("resultString = %s", resultString));
    }
    log.info("main function end");
  }
}
