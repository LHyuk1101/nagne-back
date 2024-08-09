package com.nagne.config;

import java.nio.charset.StandardCharsets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;


@Configuration
public class RestTemplateConfig {

    @Bean(name = "llmRestTemplate")
    public RestTemplate llmRestTemplate(RestTemplateBuilder builder) {
        return builder
            .additionalMessageConverters(new StringHttpMessageConverter(StandardCharsets.UTF_8))
            .build();

    }
}