package io.chagchagchag.example.foobar.user.config;

import io.chagchagchag.example.foobar.core.user.jwt.JwtProcessor;
import io.chagchagchag.example.foobar.user.config.security.JwtAuthenticationFilterBackup;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@RequiredArgsConstructor
@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

  private final JwtProcessor jwtProcessor;

  // Reactive 버전으로 재작성 예정
  @Bean
  public SecurityWebFilterChain filterChain(
      ServerHttpSecurity httpSecurity,
      AuthenticationConfiguration authenticationConfiguration
  ) throws Exception {
    var authenticationManager = authenticationConfiguration.getAuthenticationManager();
    return httpSecurity
        .csrf(csrfSpec -> csrfSpec.disable())
        .formLogin(formLoginSpec -> formLoginSpec.disable())
        .httpBasic(httpBasicSpec -> httpBasicSpec.disable())
//        .addFilterAfter()
//        .addFilterBefore(new JwtAuthenticationFilterBackup(authenticationManager, jwtProcessor))
        .headers(headerSpec -> headerSpec.frameOptions(frameOptionsSpec -> frameOptionsSpec.disable()))
        .authorizeExchange(
            authorizeExchangeSpec ->
              authorizeExchangeSpec
                  .pathMatchers("/", "/welcome", "/img/**", "/api/users/signup")
                  .permitAll()
                  .pathMatchers("/logout", "/api/users/**")
                  .hasAnyAuthority("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN")
        )
        .securityContextRepository()

  }
}
