package io.chagchagchag.example.foobar.concurrent.sync_async.simple;

import java.time.LocalTime;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleAsyncExample {
  public static void main(String [] args){
    log.info("(start) main function " + LocalTime.now());
    execLongDelayJob(i -> {
      var result = i + i;
      log.info("result == {}", result);
    });
    log.info("(end) main function " + LocalTime.now());
  }

  public static void execLongDelayJob(Consumer<Integer> consumer){
    final long current = System.currentTimeMillis();
    while(true){
      final long spent = System.currentTimeMillis() - current;
      if(spent > 1000) break;
    }

    consumer.accept(500);
  }
}
