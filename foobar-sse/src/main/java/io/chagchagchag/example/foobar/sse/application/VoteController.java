package io.chagchagchag.example.foobar.sse.application;

import java.time.Duration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

@Controller
public class VoteController {
  @ResponseBody
  @GetMapping(path = "/vote", produces = "text/event-stream")
  public Flux<String> getVote(){
    return Flux
        .interval(Duration.ofMillis(100))
        .map(ms -> "고영희 " + ms);
  }

  @ResponseBody
  @GetMapping(path = "/vote2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<ServerSentEvent<String>> getVote2(
      @RequestHeader(name = "Last-Event-ID", required = false, defaultValue = "0") Long lastEventId
  ){
    return Flux
        .range(0, 10)
        .delayElements(Duration.ofMillis(10))
        .map(
            i -> ServerSentEvent.<String>builder()
                .event("vote")
                .id(String.valueOf(i + lastEventId + 1))
                .data("data-"+i)
                .comment("comment-"+i)
                .build()
        );
  }
}
