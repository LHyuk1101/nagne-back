package com.nagne.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagne.global.error.ErrorCode;
import com.nagne.global.error.exception.ApiException;
import com.nagne.global.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;
  private final CustomCorsFilter corsFilter;

  public CustomAuthenticationEntryPoint(ObjectMapper objectMapper, CustomCorsFilter corsFilter) {
    this.objectMapper = objectMapper;
    this.corsFilter = corsFilter;
  }

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
    AuthenticationException authException) throws ApiException, IOException {
    corsFilter.setCorsHeaders(request, response);
    response.setStatus(ErrorCode.USER_UNAUTHORIZED.getStatus());
    String jsonResponse = objectMapper.writeValueAsString(
      ApiResponse.error(ErrorCode.USER_UNAUTHORIZED,
        "We couldn't find your authentication information. Please sign up for an account and try again."));
    response.getWriter().write(jsonResponse);
  }
}
