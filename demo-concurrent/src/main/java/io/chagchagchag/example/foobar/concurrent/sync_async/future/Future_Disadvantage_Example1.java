package io.chagchagchag.example.foobar.concurrent.sync_async.future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.SneakyThrows;

public class Future_Disadvantage_Example1 {
  @SneakyThrows
  public static void main(String[] args) {
    Future<String> future1 = getFuture();
    future1.cancel(true); // 이미 실행 중이라면 Interrupt 한다.
    assert future1.isDone(); // cancel 했지만, isDone() == true

    Future<String> future2 = getFutureWithException();
    Exception exception = null;
    try{
      future2.get();
    }
    catch (Exception e){
      exception = e;
    }

    assert future2.isDone();
    assert exception != null;
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

  @SneakyThrows
  public static Future<String> getFutureWithException(){
    ExecutorService executor = Executors.newSingleThreadExecutor();
    try{
      return executor.submit(() -> {
        throw new RuntimeException("배고파요");
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
