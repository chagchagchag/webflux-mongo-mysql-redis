package io.chagchagchag.example.foobar.user.config.security;

import io.chagchagchag.example.foobar.core.user.jwt.JwtDto;
import io.chagchagchag.example.foobar.core.user.jwt.JwtSupport;
import io.chagchagchag.example.foobar.dataaccess.user.security.BearerToken;
import io.chagchagchag.example.foobar.user.usecase.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {
  private final JwtSupport jwtSupport;
  private final CustomUserDetailsService userDetailsService;

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    return Mono.justOrEmpty(authentication)
        .filter(auth -> auth instanceof BearerToken)
        .cast(BearerToken.class)
        .map(bearerToken -> degenerateToken(bearerToken))
        .flatMap(jwtDto -> validateJwt(jwtDto))
        .flatMap(jwtDto -> findUserById(jwtDto.id()))
        .onErrorMap(throwable -> new IllegalArgumentException("INVALID JWT"));
  }

  public JwtDto degenerateToken(BearerToken token){
    return jwtSupport.degenerateToken(SecurityProperties.key, token.getJwt());
  }

  public Mono<JwtDto> validateJwt(JwtDto jwtDto){
    if(jwtSupport.checkIfNotExpired(jwtDto.expiration())){
      return Mono.just(jwtDto);
    }
    return Mono.error(new IllegalArgumentException("Token Invalid"));
  }

  private Mono<Authentication> findUserById(String userId){
    return userDetailsService
        .findByUsername(userId)
        .map(userDetails -> {
          var authentication = new UsernamePasswordAuthenticationToken(
              userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities()
          );
          SecurityContextHolder.getContext().setAuthentication(authentication);
          return authentication;
        });
  }

  private Mono<Authentication> validate(BearerToken token){
    JwtDto jwtDto = jwtSupport.degenerateToken(SecurityProperties.key, token.getJwt());

    if(jwtSupport.checkIfNotExpired(jwtDto.expiration())){
      return userDetailsService
          .findByUsername(jwtDto.id())
          .map(userDetails -> new UsernamePasswordAuthenticationToken(
              userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities()
          ));
    }

    throw new IllegalArgumentException("Token Invalid");
  }
}
