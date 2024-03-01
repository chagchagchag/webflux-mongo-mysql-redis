package io.chagchagchag.example.foobar.user.config.security.old;

import io.chagchagchag.example.foobar.core.user.jwt.JwtDto;
import io.chagchagchag.example.foobar.core.user.jwt.JwtProcessor;
import io.chagchagchag.example.foobar.user.usecase.SecurityProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class JwtAuthenticationFilterBackup extends AuthenticationWebFilter {

  private final ReactiveAuthenticationManager authenticationManager;
  private final JwtProcessor jwtProcessor;

  public JwtAuthenticationFilterBackup(
      ReactiveAuthenticationManager authenticationManager,
      JwtProcessor jwtProcessor
  ) {
    super(authenticationManager);
    this.authenticationManager = authenticationManager;
    this.jwtProcessor = jwtProcessor;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    exchange
        .getRequest()
        .getHeaders().get(HttpHeaders.AUTHORIZATION)
        .stream().filter(str -> StringUtils.hasText(str))
        .forEach(headerString -> {
          JwtDto jwtDto = jwtProcessor.degenerateToken(SecurityProperties.key, headerString);

          if(jwtProcessor.checkIfExpired(jwtDto.expiration())){
            throw new IllegalStateException("로그인기한 만료");
          }

          var auth = new UsernamePasswordAuthenticationToken(jwtDto.username(), jwtDto.email());
          authenticationManager.authenticate(auth);
          SecurityContextHolder.getContext().setAuthentication(auth);
        });
    return Mono.empty();
  }

  @Override
  protected Mono<Void> onAuthenticationSuccess(
      Authentication authentication,
      WebFilterExchange webFilterExchange
  ) {
    return super.onAuthenticationSuccess(authentication, webFilterExchange);
  }
}
