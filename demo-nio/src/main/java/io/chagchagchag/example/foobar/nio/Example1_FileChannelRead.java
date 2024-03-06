package io.chagchagchag.example.foobar.nio;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Example1_FileChannelRead {
  @SneakyThrows
  public static void main(String [] args){
    log.info("main function started");

    var path = Example1_FileChannelRead.class
        .getClassLoader()
        .getResource("example1-data.txt")
        .getFile();

    var file = new File(path);

    try (var fileChannel = FileChannel.open(file.toPath())){
      var byteBuffer = ByteBuffer.allocateDirect(1024);
      bufferLog("버퍼할당", byteBuffer);


      fileChannel.read(byteBuffer);
      bufferLog("읽기 연산(read) - 버퍼에 쓰는 작업", byteBuffer);

      byteBuffer.flip();
      bufferLog("읽기 전환(Flip)", byteBuffer);

      byteBuffer.rewind();
      bufferLog("첫 위치로 전환(rewind)", byteBuffer);

      log.info("buffer = {}", StandardCharsets.UTF_8.decode(byteBuffer));
      bufferLog("Buffer연산 없이 decode 수행", byteBuffer);

      log.info("buffer = {}", StandardCharsets.UTF_8.decode(byteBuffer));
      bufferLog("Buffer연산 없이 decode 수행", byteBuffer);

      byteBuffer.clear();
      bufferLog("clear", byteBuffer);
    }

    log.info("main function end");
  }

  public static void bufferLog(String operation, ByteBuffer buffer){
    log.info(String.format("%s >>> position = %s, limit = %s, capacity = %s\n", operation, buffer.position(), buffer.limit(), buffer.capacity()));
  }
}
