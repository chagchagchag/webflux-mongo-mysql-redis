package io.chagchagchag.example.data_reactive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  @Bean(name = "imageServerWebClient")
  public WebClient imageServerWebClient(){
    return WebClient.create("http://localhost:8081");
  }

}
