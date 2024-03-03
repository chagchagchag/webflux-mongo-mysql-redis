package io.chagchagchag.example.foobar.concurrent.sync_async;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AsyncNonBlockingExample {
  public static void main(String [] args){
    log.info("(start) main function " + LocalTime.now());
    execLongDelayJob(i -> {
      var result = 1 + i;
      assert result == 1112;
      log.info("result = {}", result);
    });
    log.info("(end) main function " + LocalTime.now());
  }

  public static void execLongDelayJob(Consumer<Integer> callback){
    var executor = Executors.newSingleThreadExecutor();
    try{
      executor.submit(()->{
        long start = System.currentTimeMillis();
        while(true){
          long delay = System.currentTimeMillis() - start;
          if(delay > 1000) break;
        }
        callback.accept(1111);
        log.info("작업이 끝났어요~!!! --- " + LocalTime.now());
        return 1111;
      });
    }
    catch (Exception e){
      e.printStackTrace();
      throw new RuntimeException("ERROR");
    }
    finally {
      executor.shutdown();
    }
  }
}
