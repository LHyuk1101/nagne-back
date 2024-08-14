package com.nagne.domain.user.dto;

import com.nagne.domain.user.entity.User;
import com.nagne.domain.user.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserJoinDto {
  
  @Email(message = "올바른 이메일 형식이 아닙니다.")
  @NotBlank(message = "이메일은 빈 값이 들어올 수 없습니다.")
  private String email;
  
  @NotBlank(message = "패스워드는 빈 값이 들어올 수 없습니다.")
  @Size(min = 8, max = 32, message = "패스워드는 최소 8자리 이상, 최대 32자리까지 가능합니다.")
  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,32}$", message = "패스워드는 영문, 숫자, 특수문자(!@#$%^&*) 조합으로 입력해야 합니다.")
  private String password;
  
  @NotBlank(message = "닉네임은 빈 값이 들어올 수 없습니다.")
  private String nickname;
  
  private Boolean termsAgreed; // 이용 약관 동의
  
  // 기본 생성자에서 기본값 설정
  public UserJoinDto() {
    this.termsAgreed = false;
  }
  
  public User toEntity() {
    return User.builder()
      .email(this.email)
      .password(this.password)
      .nickname(this.nickname)
      .role(UserRole.USER)
      .build();
  }
  
  public UserJoinDto setPassword(String ecryptedPassword) {
    this.password = ecryptedPassword;
    return this;
  }
}
