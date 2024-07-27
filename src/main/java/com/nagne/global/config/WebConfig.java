package org.team.bookshop.global.config;


import java.time.Duration;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Getter
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.base-url}")
    private String baseUrl;
    @Value("${app.base-image-uri}")
    private String baseImageUri;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/images/**")
            .setCacheControl(CacheControl.maxAge(Duration.ofDays(7)))
            .addResourceLocations(baseImageUri);
    }
}
