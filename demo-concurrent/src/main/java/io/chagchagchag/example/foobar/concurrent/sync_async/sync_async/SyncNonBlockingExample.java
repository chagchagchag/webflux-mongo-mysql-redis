package io.chagchagchag.example.foobar.concurrent.sync_async.sync_async;

import java.time.LocalTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SyncNonBlockingExample {
  public static void main(String [] args) throws InterruptedException, ExecutionException {
    log.info("(start) main function " + LocalTime.now());

    var count = 1;
    Future<Integer> job = doLongDelayJob();
    while(!job.isDone()){
      log.info(String.format("대기 중... %s", count++)); // 대기 중에 counting 연산을 수행
      Thread.sleep(100);
    }

    var total = job.get() + 1;
    assert total == 1112;

    log.info("(end) main function " + LocalTime.now());
  }

  public static Future<Integer> doLongDelayJob(){
    var executor = Executors.newSingleThreadExecutor();
    try{
      return executor.submit(() -> {
        long start = System.currentTimeMillis();
        while(true){
          long delay = System.currentTimeMillis() - start;
          if(delay > 1000) break;
        }
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
