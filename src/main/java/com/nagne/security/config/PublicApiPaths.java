package com.nagne.security.config;

import java.util.List;
import org.springframework.util.AntPathMatcher;

public final class PublicApiPaths {
  
  public static final List<String> PATHS = List.of(
    "/api/auth/**",
    "/api/login/**",
    "/api/place/**",
    "/api/templates/**",
    "/api/llm/**",
    "/api/populardestinations/**"
//    "/api/users/**"
  );
  
  private static final AntPathMatcher antPathMatcher = new AntPathMatcher();
  
  private PublicApiPaths() {
    // 인스턴스화 방지
  }
  
  public static boolean isPublic(String url) {
    return PATHS.stream().anyMatch(pattern -> antPathMatcher.match(pattern, url));
  }
}
