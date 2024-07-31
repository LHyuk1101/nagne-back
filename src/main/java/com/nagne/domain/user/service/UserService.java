package com.nagne.domain.user.service;

import com.nagne.domain.user.dto.UserJoinDto;
import com.nagne.domain.user.dto.UserPostDto;
import com.nagne.domain.user.dto.UserResponseDto;
import com.nagne.domain.user.entity.User;
import com.nagne.global.error.ErrorCode;
import com.nagne.domain.user.repository.UserRepository;
import com.nagne.global.error.exception.ApiException;
import com.nagne.global.error.exception.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = false)
    public void saveUser(UserJoinDto userJoinDto) throws ApiException {
        if (userRepository.findByEmail(userJoinDto.getEmail()).isPresent()) {
            throw new ApiException(ErrorCode.EMAIL_ALREADY_REGISTERED);
        }
        String encodedPassword = passwordEncoder.encode(userJoinDto.getPassword());
        User user = userJoinDto.setPassword(encodedPassword).toEntity();
        userRepository.save(user);
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        return UserResponseDto.fromEntity(user);
    }

    public List<UserPostDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserPostDto> userPostDtos = new ArrayList<>();
        for (User user : users) {
            UserPostDto userPostDto = UserPostDto.toDto(user);
            userPostDtos.add(userPostDto);
        }
        return userPostDtos;
    }

    @Transactional(readOnly = false)
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        userRepository.delete(user);
    }

    @Transactional(readOnly = false)
    public UserResponseDto updateUser(Long id, UserPostDto UserPostDto) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        user = userRepository.save(UserPostDto.toEntity(user));
        return UserResponseDto.fromEntity(user);
    }
}
