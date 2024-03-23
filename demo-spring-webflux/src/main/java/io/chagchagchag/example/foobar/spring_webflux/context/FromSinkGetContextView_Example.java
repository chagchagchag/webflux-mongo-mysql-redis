package io.chagchagchag.example.foobar.spring_webflux.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.util.context.Context;

@Slf4j
public class FromSinkGetContextView_Example {
  public static void main(String[] args) {
    var initialContext = Context.of("message", "배고파요");

    Flux.create(fluxSink -> {
      var name = fluxSink.contextView().get("message");
      log.info("Publisher 의 첫 Create (Sink) 시의 message = " + name);
      fluxSink.next("aaa");
    })
    .contextWrite(context -> context.put("message", "배불러요"))
    .subscribe(null, null, null, initialContext);
  }
}
