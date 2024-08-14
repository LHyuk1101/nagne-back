package com.nagne.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TokenResponseDto {

  private String accessToken;
  private Long userId;
  private String nickname;
  private String email;
  private String role;
  private String userProfileImg;
}
