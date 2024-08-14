package com.nagne.domain.user.dto;

import com.nagne.domain.user.entity.User;
import com.nagne.domain.user.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserPostDto {
  
  @Email(message = "올바른 이메일 형식이 아닙니다.")
  @NotBlank(message = "이메일은 빈 값이 들어올 수 없습니다.")
  private String email;
  
  @NotBlank(message = "닉네임은 빈 값이 들어올 수 없습니다.")
  private String nickname;
  
  private String profileImg;
  
  private Integer nation;
  
  public static UserPostDto toDto(User user) {
    return UserPostDto.builder()
      .email(user.getEmail())
      .nickname(user.getNickname())
      .profileImg(user.getProfileImg())
      .nation(user.getNation())
      .build();
  }
  
  public User toEntity() {
    return User.builder()
      .email(this.email)
      .nickname(this.nickname)
      .role(UserRole.USER)
      .build();
  }
  
  public User toEntity(User user) {
    return User.builder()
      .id(user.getId())
      .password(user.getPassword())
      .email(this.email != null ? this.email : user.getEmail())
      .nickname(this.nickname != null ? this.nickname : user.getNickname())
      .role(user.getRole())
      .build();
  }
}
