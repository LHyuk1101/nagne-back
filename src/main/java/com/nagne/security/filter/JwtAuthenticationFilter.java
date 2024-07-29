package com.nagne.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagne.config.WebConfig;
import com.nagne.domain.user.entity.User;
import com.nagne.domain.user.repository.UserRepository;
import com.nagne.security.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final WebConfig webConfig;

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
                responseUnauthorized(response,
                    "Token refreshed, please retry with new access token", newAccessToken);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        String method = request.getMethod();
        return method.equals("OPTIONS") || //preflight 요청을 처리하기위해 사용
            path.startsWith("/api/auth") ||
            path.startsWith("/api/users") ||
            path.startsWith("/api/categories") && method.equals("GET") ||
            path.startsWith("/api/products") && method.equals("GET") ||
            path.startsWith("/api/images");
    }

    private void responseUnauthorized(HttpServletResponse response, String message,
        String newAccessToken) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.setHeader("Access-Control-Allow-Origin", webConfig.getBaseUrl());
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, X-Requested-With");
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + newAccessToken);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", message);
        responseBody.put("status", "401");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(responseBody);

        response.getWriter().write(jsonResponse);
    }

    private void setSecurityContext(String token, HttpServletRequest request) {
        Long userId = Long.valueOf(jwtTokenProvider.getUserId(token));
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user, null, List.of(new SimpleGrantedAuthority(user.getRole().getRoleName())));
            authenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }

}

