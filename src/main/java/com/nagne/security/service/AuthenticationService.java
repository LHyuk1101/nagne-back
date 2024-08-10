package com.nagne.security.service;

import com.nagne.domain.user.dto.UserLoginDto;
import com.nagne.domain.user.entity.User;
import com.nagne.domain.user.repository.UserRepository;
import com.nagne.global.error.ErrorCode;
import com.nagne.global.error.exception.ApiException;
import com.nagne.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public User login(UserLoginDto userLoginDto) throws ApiException {
    User user = userRepository.findByEmail(userLoginDto.getEmail())
      .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

    if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
      throw new ApiException("Invalid credentials", ErrorCode.AUTHENTICATION_FAILURE);
    }

    return user;
  }
}
