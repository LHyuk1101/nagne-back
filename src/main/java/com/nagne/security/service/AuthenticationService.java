package com.nagne.security.service;

import com.nagne.domain.user.dto.UserLoginDto;
import com.nagne.domain.user.entity.OauthProvider;
import com.nagne.domain.user.entity.Oauthid;
import com.nagne.domain.user.entity.User;
import com.nagne.domain.user.entity.UserRole;
import com.nagne.domain.user.repository.OauthRepository;
import com.nagne.domain.user.repository.UserRepository;
import com.nagne.global.error.ErrorCode;
import com.nagne.global.error.exception.ApiException;
import com.nagne.global.error.exception.EntityNotFoundException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final OauthRepository oauthRepository;
    private final PasswordEncoder passwordEncoder;

    public User login(UserLoginDto userLoginDto) throws ApiException {
        User user = userRepository.findByEmail(userLoginDto.getEmail())
            .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new ApiException("Invalid credentials", ErrorCode.AUTHENTICATION_FAILURE);
        }

        return user;
    }

    public User findOrCreateUser(String provider, Map<String, Object> attributes,
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
