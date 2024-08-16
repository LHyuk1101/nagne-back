package com.nagne.domain.user.entity;

import lombok.Getter;

@Getter
public enum UserRole {
  ADMIN("ADMIN", "관리자"),
  USER("USER", "유저");

  private final String roleName;
  private final String description;

  UserRole(String role, String description) {
    this.roleName = role;
    this.description = description;
  }
}
