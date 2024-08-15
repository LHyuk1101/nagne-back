package com.nagne.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagne.global.error.ErrorCode;
import com.nagne.global.response.ApiResponse;
import com.nagne.security.filter.CustomCorsFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomOAuth2FailureHandler implements AuthenticationFailureHandler {

  private final CustomCorsFilter corsFilter;
  private final ObjectMapper objectMapper;

  public CustomOAuth2FailureHandler(CustomCorsFilter corsFilter, ObjectMapper objectMapper) {
    this.corsFilter = corsFilter;
    this.objectMapper = objectMapper;
  }

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
    AuthenticationException exception) throws IOException, ServletException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    corsFilter.setCorsHeaders(request, response);
    response.setStatus(ErrorCode.USER_UNAUTHORIZED.getStatus());
    String jsonResponse = objectMapper.writeValueAsString(
      ApiResponse.error(ErrorCode.USER_UNAUTHORIZED, "Authentication failed. Please try again."));
    response.getWriter().write(jsonResponse);
  }
}