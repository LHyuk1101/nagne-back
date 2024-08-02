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
    private final UserRepository userRepository;
    private final OauthRepository oauthRepository;
    private final ObjectMapper objectMapper;
    private final WebConfig webConfig;
    private final OAuth2AuthorizedClientService authorizedClientService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
            oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());

        Map<String, Object> attributes = oauthToken.getPrincipal().getAttributes();
        String provider = oauthToken.getAuthorizedClientRegistrationId();

        User user = findOrCreateUser(provider, attributes, authorizedClient);

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

    private User findOrCreateUser(String provider, Map<String, Object> attributes,
        OAuth2AuthorizedClient authorizedClient) {
        final String providerId;
        final String email;
        final String name;
        final String nickname;
        final String profileImg;

        switch (provider) {
            case "google":
                providerId = (String) attributes.get("sub");
                email = (String) attributes.get("email");
                name = (String) attributes.get("name");
                profileImg = (String) attributes.get("picture");
                nickname = name; // Google은 nickname 속성을 따로 제공하지 않음
                break;

            default:
                throw new IllegalArgumentException("Unknown provider: " + provider);
        }


        // 기존 user가 존재 할 경우
        User findUser = userRepository.findByEmail(email).orElse(null);
        if (findUser != null) {
            return findUser;
        }

        User user = User.builder()
            .email(email)
            .nickname(nickname)
            .profileImg(profileImg)
            .role(UserRole.USER)
            .build();

        User savedUser = userRepository.save(user);

        if(savedUser == null && savedUser.getId() == null) {
            throw new ApiException("Error occurred while creating Oauth2 user!", ErrorCode.OAUTH2_CREATE_USER_ERROR);
        }


        String accessToken = null;
        String refreshToken = null;

        try{
            accessToken = authorizedClient.getAccessToken().getTokenValue();
            refreshToken = authorizedClient.getRefreshToken().getTokenValue();
        }catch (Exception e)
        {
        }

        Oauthid oauthid = Oauthid.builder()
            .provider(OauthProvider.GOOGLE)
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .user(savedUser)
            .build();

        oauthRepository.save(oauthid);

        return savedUser;
    }
}
