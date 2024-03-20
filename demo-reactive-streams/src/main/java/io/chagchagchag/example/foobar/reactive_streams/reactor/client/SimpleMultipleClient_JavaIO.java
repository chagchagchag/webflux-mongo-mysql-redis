package io.chagchagchag.example.foobar.reactive_streams.reactor.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleMultipleClient_JavaIO {
  private static ExecutorService executorService = Executors.newFixedThreadPool(30);

  @SneakyThrows
  public static void main(String[] args) {
    log.info(">>> main START !!! ");
    List<CompletableFuture<Void>> futures = new ArrayList<>();

    long start = System.currentTimeMillis();

    for(int i=0; i<1000; i++){
      var future = CompletableFuture.runAsync(() -> {
        try(Socket socket = new Socket()){
          socket.connect(new InetSocketAddress("localhost", 8080));
          OutputStream out = socket.getOutputStream();

          String message = "안녕하세요";
          out.write(message.getBytes());

          InputStream in = socket.getInputStream();
          byte [] responseBytes = new byte[1024];
          in.read(responseBytes);
          log.info("result {}", new String(responseBytes).trim());
        }
        catch (Exception e){
          log.error(e.getMessage());
        }
      }, executorService);

      futures.add(future);
    }

    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    executorService.shutdown();
    log.info(">>> main END !!!");
    long end = System.currentTimeMillis();
    log.info("duration : {}", (end - start) / 1000.0);
  }
}
