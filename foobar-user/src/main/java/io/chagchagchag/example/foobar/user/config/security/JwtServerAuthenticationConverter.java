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
        .flatMap(serverHttpRequest -> Mono.justOrEmpty(serverHttpRequest.getHeaders()))
        .flatMap(httpHeaders -> Mono.justOrEmpty(httpHeaders.getFirst(HttpHeaders.AUTHORIZATION)))
        .filter(headerValue -> checkContainsBearer(headerValue))
        .flatMap(jwt -> Mono.justOrEmpty(new BearerToken(jwt)));
  }

  public Boolean checkContainsBearer(String header){
    var len = "Bearer ".length();
    return header.substring(0, len).equalsIgnoreCase("Bearer");
  }

}
