package io.chagchagchag.example.foobar.concurrent.sync_async.future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FutureCancel_Methods_Example1 {
  @SneakyThrows
  public static void main(String[] args) {
    Future<String> future1 = getFuture();
    Boolean isCancelled1 = future1.cancel(true);
    // mayInterruptIfRunning = true 의 의미 :: 작업이 진행 중이면 Interrupt 하겠다.

    assert future1.isCancelled();
    assert future1.isDone();
    assert isCancelled1 == true;

    // 이미 존재하는 Future 를 Cancel
    Boolean isCancelled2 = future1.cancel(true);
    assert future1.isCancelled();
    assert future1.isDone(); // cancel() 된 작업도 isDone() == true 로 간주
    assert isCancelled2 == false;
  }

  @SneakyThrows
  public static Future<String> getFuture(){
    ExecutorService executor = Executors.newSingleThreadExecutor();
    try{
      return executor.submit(() -> {
        return "안녕하세요";
      });
    }
    catch (Exception e){
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    finally {
      executor.shutdown();
    }
  }
}
