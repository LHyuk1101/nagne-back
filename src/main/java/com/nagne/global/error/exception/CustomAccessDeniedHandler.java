package com.nagne.global.error.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagne.global.error.ErrorCode;
import com.nagne.global.response.ApiResponse;
import com.nagne.security.filter.CustomCorsFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;
  private final CustomCorsFilter corsFilter;

  public CustomAccessDeniedHandler(ObjectMapper objectMapper, CustomCorsFilter corsFilter) {
    this.objectMapper = objectMapper;
    this.corsFilter = corsFilter;
  }

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
    AccessDeniedException accessDeniedException) throws IOException {
    corsFilter.setCorsHeaders(request, response);
    response.setStatus(ErrorCode.FORBIDDEN.getStatus());
    String jsonResponse = objectMapper.writeValueAsString(
      ApiResponse.error(ErrorCode.FORBIDDEN, ""));
    response.getWriter().write(jsonResponse);
  }

}
