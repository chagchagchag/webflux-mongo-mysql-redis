package io.chagchagchag.example.foobar.user.config;

import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

//@Configuration
public class CorsGlobalConfig implements WebFluxConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedOrigins("http://loalhost:3000")
        .allowedMethods("*");
  }
}
