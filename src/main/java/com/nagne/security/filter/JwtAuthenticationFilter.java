package com.nagne.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagne.domain.user.entity.User;
import com.nagne.domain.user.repository.UserRepository;
import com.nagne.global.error.ErrorCode;
import com.nagne.global.response.ApiResponse;
import com.nagne.security.config.PublicApiPaths;
import com.nagne.security.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final CustomCorsFilter corsFilter;

  @Override
  protected void doFilterInternal(@NotNull HttpServletRequest request,
    @NotNull HttpServletResponse response,
    @NotNull FilterChain filterChain) throws ServletException, IOException {

    String jwtAccessToken = jwtTokenProvider.getJwtFromRequest(request);

    if (jwtAccessToken != null && jwtTokenProvider.validateAccessToken(jwtAccessToken)) {
      setSecurityContext(jwtAccessToken, request);
    } else {
      String refreshToken = jwtTokenProvider.getRefreshTokenFromCookies(request);
      if (refreshToken != null && jwtTokenProvider.validateRefreshToken(refreshToken)) {
        String newAccessToken = jwtTokenProvider.updateAccessToken(refreshToken);
        setSecurityContext(newAccessToken, request);
        responseWithNewAccessToken(request, response, newAccessToken);
        return;
      }
    }

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String path = request.getRequestURI();
    return PublicApiPaths.isPublic(path);
  }

  private void responseWithNewAccessToken(HttpServletRequest request, HttpServletResponse response,
    String newAccessToken)
    throws IOException {
    corsFilter.setCorsHeaders(request, response);
    response.setStatus(ErrorCode.USER_UNAUTHORIZED.getStatus());
    response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + newAccessToken);
    ObjectMapper objectMapper = new ObjectMapper();
    String jsonResponse = objectMapper.writeValueAsString(
      ApiResponse.error(ErrorCode.USER_UNAUTHORIZED,
        "If the issue persists, please clear your browser cookies and try again."));
    response.getWriter().write(jsonResponse);
  }

  private void setSecurityContext(String token, HttpServletRequest request) {
    Long userId = Long.valueOf(jwtTokenProvider.getUserId(token));
    User user = userRepository.findById(userId).orElse(null);
    if (user != null) {
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        user, null, List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleName())));
      authenticationToken.setDetails(
        new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
  }

}

