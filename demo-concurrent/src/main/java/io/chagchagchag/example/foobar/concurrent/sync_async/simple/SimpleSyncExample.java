package io.chagchagchag.example.foobar.concurrent.sync_async.simple;

import java.time.LocalTime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleSyncExample {
  public static void main(String [] args){
    log.info("(start) main function " + LocalTime.now());
    var result = getLongDelayJob();
    var increased = result + 1;
    assert increased == 501;
    log.info("(end) main function " + LocalTime.now());
  }

  public static int getLongDelayJob(){
    long current = System.currentTimeMillis();
    while(true){
      long timeSpent = System.currentTimeMillis() - current;
      if(timeSpent > 1000) break;
    }
    return 500;
  }
}
