package io.chagchagchag.example.foobar.user.config.security;

import io.chagchagchag.example.foobar.dataaccess.user.security.BearerToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class JwtServerAuthenticationConverter implements ServerAuthenticationConverter {

  @Override
  public Mono<Authentication> convert(ServerWebExchange exchange) {
    return Mono.justOrEmpty(exchange.getRequest())
        .switchIfEmpty(Mono.error(new IllegalArgumentException("_____request 가 올바르지 않습니다.")))
        .map(serverHttpRequest -> serverHttpRequest.getHeaders())
        .switchIfEmpty(Mono.error(new IllegalArgumentException("_____header 가 비어 있습니다.")))
        .map(httpHeaders -> httpHeaders.getFirst(HttpHeaders.AUTHORIZATION))
        .switchIfEmpty(Mono.error(new IllegalArgumentException("_____Authorization 헤더가 누락되었습니다.")))
        .filter(headerValue -> checkContainsBearer(headerValue))
        .switchIfEmpty(Mono.error(new IllegalArgumentException("_____Bearer 를 전달받지 못했습니다.")))
        .map(jwt -> new BearerToken(jwt));
  }

  public Boolean checkContainsBearer(String header){
    var len = "Bearer ".length();
    return header.substring(0, len).equalsIgnoreCase("Bearer");
  }

}
