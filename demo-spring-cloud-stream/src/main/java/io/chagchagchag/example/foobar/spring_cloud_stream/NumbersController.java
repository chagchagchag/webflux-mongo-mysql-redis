package io.chagchagchag.example.foobar.spring_cloud_stream;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/numbers")
@RestController
public class NumbersController {
  private final StreamBridge streamBridge;

  @GetMapping("/to-list")
  public void toList(@RequestParam("word") String word){
    streamBridge.send("toLengthList-in-0", word);
  }
}
