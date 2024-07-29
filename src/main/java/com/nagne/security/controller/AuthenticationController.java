package com.nagne.security.controller;

import com.nagne.domain.user.dto.TokenResponseDto;
import com.nagne.domain.user.dto.UserLoginDto;
import com.nagne.domain.user.entity.User;
import com.nagne.global.error.exception.EntityNotFoundException;
import com.nagne.security.jwt.JwtTokenProvider;
import com.nagne.security.service.AuthenticationService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nagne.domain.user.repository.UserRepository;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody UserLoginDto userLoginDto,
                                                  HttpServletResponse response) {
        User user = authenticationService.login(userLoginDto);

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
        return ResponseEntity.ok(tokenResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        log.info("Logout request received");

        response.addCookie(jwtTokenProvider.setRefreshTokenToCookies(null));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtTokenProvider.getRefreshTokenFromCookies(request);

        try {
            if (refreshToken != null && jwtTokenProvider.validateRefreshToken(refreshToken)) {
                String newAccessToken = jwtTokenProvider.updateAccessToken(refreshToken);
                String newRefreshToken = jwtTokenProvider.updateRefreshToken(refreshToken);
                response.addCookie(jwtTokenProvider.setRefreshTokenToCookies(newRefreshToken));

                Long userId = Long.valueOf(jwtTokenProvider.getUserId(newAccessToken));
                User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

                TokenResponseDto tokenResponseDto = TokenResponseDto.builder()
                    .accessToken(newAccessToken)
                    .userId(user.getId())
                    .nickname(user.getNickname())
                    .email(user.getEmail())
                    .role(user.getRole().getRoleName())
                    .build();
                return ResponseEntity.ok(tokenResponseDto);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}


