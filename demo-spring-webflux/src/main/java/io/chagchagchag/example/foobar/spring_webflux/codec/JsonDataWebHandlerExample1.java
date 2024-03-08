package io.chagchagchag.example.foobar.spring_webflux.codec;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

@Slf4j
public class JsonDataWebHandlerExample1 {
  private static record TickerQuery(
      String ticker
  ){}

  @SneakyThrows
  public static void main(String[] args) {
    log.info("main function started");

    var webHandler = new WebHandler(){
      @Override
      public Mono<Void> handle(ServerWebExchange exchange) {
        ServerCodecConfigurer codecConfigurer = ServerCodecConfigurer.create();
        var request = ServerRequest.create(
            exchange, codecConfigurer.getReaders()
        );

        var response = exchange.getResponse();

        var bodyMono = request.bodyToMono(TickerQuery.class);
        return bodyMono.flatMap(query -> {
          String tickerQuery = query.ticker();
          String ticker = tickerQuery == null ? "MSFT" : tickerQuery;

          String content = "You picked " + ticker;
          log.info("content = {}", content);

          Mono<DataBuffer> responseBody = Mono.just(
              response.bufferFactory().wrap(content.getBytes())
          );

          response.getHeaders()
              .add("Content-Type", "text/plain");

          return response.writeWith(responseBody);
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
