package io.chagchagchag.example.foobar.spring_webflux;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

@Slf4j
public class WebHandlerExample1_AcceptOnlyJson {
  private static record TickerRecord(
      String ticker
  ){

  }

  @SneakyThrows
  public static void main(String[] args) {
    var codecConfigurer = ServerCodecConfigurer.create();
    var webHandler = new WebHandler(){
      @Override
      public Mono<Void> handle(ServerWebExchange exchange) {
        final ServerRequest request = ServerRequest.create(exchange, codecConfigurer.getReaders());
        final ServerHttpResponse response = exchange.getResponse();

        var bodyToMono = request.bodyToMono(TickerRecord.class);
        return bodyToMono.flatMap(tickerRecord -> {
          String tickerQuery = tickerRecord.ticker();
          String ticker = tickerQuery == null ? "NVDA" : tickerQuery;

          String content = "You picked " + ticker;
          log.info("responseBody : {}", content);

          Mono<DataBuffer> responseBody = Mono.just(
              response.bufferFactory().wrap(content.getBytes())
          );

          response.getHeaders().add("Content-Type", "text/plain");
          return response.writeWith(responseBody);
        });
      }
    };

    final HttpHandler httpHandler = WebHttpHandlerBuilder
        .webHandler(webHandler)
        .build();

    final var adapter = new ReactorHttpHandlerAdapter(httpHandler);
    HttpServer.create()
        .host("localhost").port(8080)
        .handle(adapter)
        .bindNow()
        .channel().closeFuture().sync();
  }
}
