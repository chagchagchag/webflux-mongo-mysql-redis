package io.chagchagchag.example.foobar.spring_webflux;

import java.net.URI;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.RequestPath;

@Slf4j
public class URIExample1 {
  @SneakyThrows
  public static void main(String[] args) {
    URI uri = new URI("http://localhost:8080/order/coffee?number=1#detail");
    RequestPath requestPath = RequestPath.parse(uri, "/order");
    log.info("requestPath.pathWithinApplication() : {}", requestPath.pathWithinApplication());
    log.info("requestPath.contextPath() : {}", requestPath.contextPath());
  }
}
