package com.nagne.domain.user.controller;

import com.nagne.domain.plan.dto.PlanDto;
import com.nagne.domain.user.dto.UserJoinDto;
import com.nagne.domain.user.dto.UserPostDto;
import com.nagne.domain.user.dto.UserResponseDto;
import com.nagne.domain.user.service.UserService;
import com.nagne.global.error.ErrorCode;
import com.nagne.global.error.exception.ApiException;
import com.nagne.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Tag(name = "유저")
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @PreAuthorize("hasAnyRole('ADMIN')")
  @GetMapping
  public ApiResponse<?> getAllUsers() {
    List<UserPostDto> userList = userService.getAllUsers();
    return ApiResponse.success(userList);
  }

  @PostMapping
  public ApiResponse<?> saveUser(@RequestBody @Valid UserJoinDto userJoinDto,
    BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new ApiException(ErrorCode.INVALID_INPUT_VALUE);
    }
    userService.saveUser(userJoinDto);
    return ApiResponse.success();
  }

  @GetMapping("/{id}")
  public ApiResponse<UserResponseDto> getUserById(@PathVariable Long id) {
    UserResponseDto userResponseDto = userService.getUserById(id);
    return ApiResponse.success(userResponseDto);
  }

  @PutMapping("/{id}")
  public ApiResponse<UserResponseDto> updateUser(@PathVariable Long id,
    @RequestBody UserPostDto userPostDto) {
    UserResponseDto updatedUser = userService.updateUser(id, userPostDto);
    return ApiResponse.success(updatedUser);
  }

  @DeleteMapping("/{id}")
  public ApiResponse<?> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ApiResponse.success();
  }

  @GetMapping("/{userId}/plans")
  public ApiResponse<?> getUserPlans(@PathVariable Long userId) {
    List<PlanDto> userPlanListDto = userService.getUserPlanList(userId);
    if(userPlanListDto == null) {
      return ApiResponse.success(null);
    }
    return ApiResponse.success(userPlanListDto);
  }
}
