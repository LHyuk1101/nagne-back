package com.nagne.security.service;

import com.nagne.domain.user.entity.User;
import com.nagne.domain.user.repository.UserRepository;
import com.nagne.global.error.exception.EntityNotFoundException;
import com.nagne.security.jwt.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
  
  private final UserRepository userRepository;
  
  @Override
  public UserDetails loadUserByUsername(String userId) throws EntityNotFoundException {
    Long id = Long.valueOf(userId);
    User user = userRepository.findById(id).orElseThrow(() -> {
      return new EntityNotFoundException("해당 유저를 찾을 수 없습니다.");
    });
    
    return new PrincipalDetails(user);
  }
}
