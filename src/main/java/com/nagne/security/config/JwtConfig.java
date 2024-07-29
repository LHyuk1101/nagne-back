package com.nagne.security.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class JwtConfig {

    @Value("${jwt.secret.key}")
    private String secretKey;
    public static final String REFRESH_JWT_COOKIE_NAME = "USR_JWT";
    public static final Long ACCESS_TOKEN_EXPIRATION_TIME = (long) (1000 * 60 * 60); // Access 토큰 만료 시간 1시간
    public static final Long REFRESH_TOKEN_EXPIRATION_TIME = (long) (1000 * 60 * 60 * 24 * 7); // refresh 토큰 만료일 7일
    public static final int JWT_COOKIE_MAX_AGE = 60 * 60 * 24; // 쿠키 만료일
}
