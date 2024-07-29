package com.nagne.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagne.config.WebConfig;
import com.nagne.domain.user.dto.TokenResponseDto;
import com.nagne.domain.user.entity.User;
import com.nagne.domain.user.entity.UserRole;
import com.nagne.domain.user.repository.UserRepository;
import com.nagne.security.jwt.JwtTokenProvider;
import com.nagne.security.config.JwtConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler,
    LogoutSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final WebConfig webConfig;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        Map<String, Object> attributes = oauthToken.getPrincipal().getAttributes();
        String provider = oauthToken.getAuthorizedClientRegistrationId();

        User user = findOrCreateUser(provider, attributes);

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

    private User findOrCreateUser(String provider, Map<String, Object> attributes) {
        final String providerId;
        final String email;
        final String name;
        final String nickname;

        switch (provider) {
            case "google":
                providerId = (String) attributes.get("sub");
                email = (String) attributes.get("email");
                name = (String) attributes.get("name");
                nickname = name; // Google은 nickname 속성을 따로 제공하지 않음
                break;

            default:
                throw new IllegalArgumentException("Unknown provider: " + provider);
        }

        return userRepository.findByProviderId(providerId).orElseGet(() -> {
            return userRepository.save(User.builder()
                .email(email)
                .nickname(nickname)
                .role(UserRole.USER)
                .build());
        });
    }
}
