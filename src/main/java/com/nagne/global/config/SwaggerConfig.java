package com.nagne.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

  private final String REST_API_ROOT = "/api/**";
  private final String REST_API_GROUP = "REST API";

  @Bean
  public OpenAPI springShopOpenAPI() {
    String jwt = "JWT";
    SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
    Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme().name(jwt)
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT"));

    return new OpenAPI()
            .components(components)
            .addSecurityItem(securityRequirement)
            .info(new Info().title("나그네 backend API")
                    .description("나그네의 BackEnd RestApi입니다.")
                    .version("v0.0.1")
                    .license(new License().name("Apache 2.0").url("http://springdoc.org")));
  }

  @Bean
  public GroupedOpenApi restApi() {

    return GroupedOpenApi.builder()
            .pathsToMatch(REST_API_ROOT)
            .group(REST_API_GROUP)
            .build();
  }


}
