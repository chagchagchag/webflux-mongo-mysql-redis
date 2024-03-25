package io.chagchagchag.example.foobar.concurrent.sync_async.future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FutureGet_Methods_Example1 {
  @SneakyThrows
  public static void main(String[] args) {
    // get()
    Future<String> future1 = getFutureWithDelay1s();
    assert !future1.isDone();
    assert !future1.isCancelled();

    // 블로킹 방식의 동기연산. 값을 얻어옵니다.
    String result1 = future1.get(); // 1초 소요
    assert result1.equals("안녕하세요");
    assert future1.isDone(); // 작업은 완료되었기에 true 를 return
    assert !future1.isCancelled(); // 취소된 적 없으므로 false 를 return

    // get(timeout, TimeUnit)
    Future<String> future2 = getFutureWithDelay1s(); // 1초 소요
    String result2 = future2.get(2000, TimeUnit.MILLISECONDS); // 2초 안에 마무리 되므로 정상수행
    assert result2.equals("안녕하세요"); // 값을 제대로 받아왔습니다.

    // 이번에는 long running 작업의 timeout 적용 코드
    Future<String> future3 = getFutureWithDelay1s(); // 1초 소요
    Exception exception = null; // exception 객체
    String result3 = null;

    try{
      result3 = future3.get(10, TimeUnit.MILLISECONDS); // 10ms 안에 마무리 되지 않으므로 TimeoutException throw
    } catch (Exception e){
      exception = e;
    }
    assert exception != null;
    assert result3 == null;
    log.info("exception = " + exception);
  }

  @SneakyThrows
  public static Future<String> getFutureWithDelay1s(){
    ExecutorService executor = Executors.newSingleThreadExecutor();
    try{
      return executor.submit(() -> {
        Thread.sleep(1000);
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
