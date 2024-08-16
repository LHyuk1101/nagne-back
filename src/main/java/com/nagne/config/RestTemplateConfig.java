package com.nagne.config;

import java.nio.charset.StandardCharsets;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
  
  @Bean(name = "llmRestTemplate")
  public RestTemplate llmRestTemplate(RestTemplateBuilder builder) {
    return builder
      .setConnectTimeout(Duration.ofSeconds(80))
      .setReadTimeout(Duration.ofSeconds(80))
      .additionalMessageConverters(new StringHttpMessageConverter(StandardCharsets.UTF_8))
      .build();
  }
}