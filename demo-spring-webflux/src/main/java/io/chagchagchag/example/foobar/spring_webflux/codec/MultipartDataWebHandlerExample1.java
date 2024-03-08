package io.chagchagchag.example.foobar.spring_webflux.codec;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.ResponseCookie;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

@Slf4j
public class MultipartDataWebHandlerExample1 {
  @SneakyThrows
  public static void main(String[] args) {
    log.info("main function started");

    var webHandler = new WebHandler(){
      @Override
      public Mono<Void> handle(ServerWebExchange exchange) {
        return exchange.getMultipartData()
            .map(multiValueMap -> {
              return ((FormFieldPart) multiValueMap.getFirst("ticker")).value();
            })
            .flatMap(tickerQuery -> {
              String ticker = tickerQuery == null ? "MSFT" : tickerQuery;
              String content = "You picked " + ticker;
              log.info("content = {}", content);

              Mono<DataBuffer> responseBody = Mono.just(
                  exchange.getResponse()
                      .bufferFactory()
                      .wrap(content.getBytes())
              );

              exchange.getResponse().addCookie(
                  ResponseCookie.from("ticker", ticker).build()
              );

              exchange.getResponse().getHeaders()
                  .add("Content-Type", "text/plain");

              return exchange.getResponse().writeWith(responseBody);
            });
      }
    };

    var httpHandler = WebHttpHandlerBuilder
        .webHandler(webHandler)
        .build();

    var handlerAdapter = new ReactorHttpHandlerAdapter(httpHandler);

    HttpServer.create()
        .host("localhost").port(8080)
        .handle(handlerAdapter)
        .bindNow()
        .channel().closeFuture().sync();

    log.info("main function end");
  }
}
