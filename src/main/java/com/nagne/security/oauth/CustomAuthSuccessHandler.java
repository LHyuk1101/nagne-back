package com.nagne.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagne.config.WebConfig;
import com.nagne.domain.user.dto.TokenResponseDto;
import com.nagne.domain.user.entity.OauthProvider;
import com.nagne.domain.user.entity.Oauthid;
import com.nagne.domain.user.entity.User;
import com.nagne.domain.user.entity.UserRole;
import com.nagne.domain.user.repository.OauthRepository;
import com.nagne.domain.user.repository.UserRepository;
import com.nagne.global.error.ErrorCode;
import com.nagne.global.error.exception.ApiException;
import com.nagne.security.config.JwtConfig;
import com.nagne.security.jwt.JwtTokenProvider;
import com.nagne.security.service.AuthenticationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler,
    LogoutSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private AuthenticationService authenticationService;
    private final ObjectMapper objectMapper;
    private final WebConfig webConfig;
    private final OAuth2AuthorizedClientService authorizedClientService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        String provider = oauthToken.getAuthorizedClientRegistrationId();
        Map<String, Object> attributes = oauthToken.getPrincipal().getAttributes();
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
            oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());

        User user = authenticationService.findOrCreateUser(provider, attributes, authorizedClient);

        String accessToken = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        response.addCookie(jwtTokenProvider.setRefreshTokenToCookies(refreshToken));

        TokenResponseDto tokenResponseDto = TokenResponseDto.builder()
                .accessToken(accessToken)
                .userId(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .role(user.getRole().getRoleName())
                .build();

        // JSON 데이터를 HTML 페이지에 포함시켜 부모 창으로 메시지를 보냅니다
        String json = objectMapper.writeValueAsString(tokenResponseDto);
        String html = "<html><body><script>" +
                "window.opener.postMessage(" + json + ", '*');" +
                "window.close();" +
                "</script></body></html>";

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(html);
    }


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException {
        Cookie cookie = new Cookie(JwtConfig.REFRESH_JWT_COOKIE_NAME, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(request.isSecure());
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        response.sendRedirect(webConfig.getBaseUrl());
    }
}
