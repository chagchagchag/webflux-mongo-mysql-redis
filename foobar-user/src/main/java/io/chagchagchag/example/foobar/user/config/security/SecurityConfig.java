package io.chagchagchag.example.foobar.user.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration
public class SecurityConfig {

  @Bean
  public SecurityWebFilterChain filterChain(
      ServerHttpSecurity httpSecurity,
      JwtServerAuthenticationConverter converter,
      JwtAuthenticationManager authenticationManager
  ){
    var authenticationWebFilter = new AuthenticationWebFilter(authenticationManager);
    authenticationWebFilter.setServerAuthenticationConverter(converter);

    return httpSecurity
        .exceptionHandling(exceptionHandlingSpec ->
            exceptionHandlingSpec.authenticationEntryPoint(
                (exchange, ex) -> Mono.fromRunnable(() -> {
                  exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                  exchange.getResponse().getHeaders().set(HttpHeaders.WWW_AUTHENTICATE, "Bearer");
                })
            )
        )
        .csrf(csrfSpec -> csrfSpec.disable())
        .formLogin(formLoginSpec -> formLoginSpec.disable())
        .httpBasic(httpBasicSpec -> httpBasicSpec.disable())
        .authorizeExchange(authorizeExchangeSpec ->
            authorizeExchangeSpec
                .pathMatchers("/", "/welcome", "/img/**", "/api/users/signup", "/healthcheck/**")
                .permitAll()
                .pathMatchers("/swagger-ui.html", "/webjars/**")
                .permitAll()
                .pathMatchers("/healthcheck/ready")
                .permitAll()
                .pathMatchers("/api/users/login", "/api/users/signup")
                .permitAll()
                .pathMatchers("/logout", "/api/users/profile/**")
                .hasAnyAuthority("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN")
        )
        .headers(headerSpec -> headerSpec.frameOptions(frameOptionsSpec -> frameOptionsSpec.disable()))
        .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
        .build();
  }
}
