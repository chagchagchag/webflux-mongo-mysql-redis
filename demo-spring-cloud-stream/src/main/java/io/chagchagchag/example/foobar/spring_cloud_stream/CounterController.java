package io.chagchagchag.example.foobar.spring_cloud_stream;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CounterController {
  private final StreamBridge streamBridge;
  @GetMapping("/increment")
  public void increment(@RequestParam("ticker") String ticker){
    streamBridge.send("increment-in-0", ticker);
  }
}
