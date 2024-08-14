package com.nagne.config;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.http.CacheControl;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import java.time.Duration;

@Getter
@Configuration
public class WebConfig implements WebMvcConfigurer {
  
  @Value("${app.base-url}")
  private String baseUrl;
//    추후 img updloade 기능 구현 시 사용
//    @Value("${app.base-image-uri}")
//    private String baseImageUri;

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/api/images/**")
//            .setCacheControl(CacheControl.maxAge(Duration.ofDays(7)))
//            .addResourceLocations(baseImageUri);
//    }
}
