package com.nagne.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {
  
  @Email(message = "올바른 이메일 형식이 아닙니다.")
  @NotBlank(message = "이메일은 빈 값이 들어올 수 없습니다.")
  private String email;
  
  @NotBlank(message = "패스워드는 빈 값이 들어올 수 없습니다.")
  @Size(min = 8, max = 32, message = "패스워드는 최소 8자리 이상, 최대 32자리까지 가능합니다.")
  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,32}$", message = "패스워드는 영문, 숫자, 특수문자(!@#$%^&*) 조합으로 입력해야 합니다.")
  private String password;
  
}
