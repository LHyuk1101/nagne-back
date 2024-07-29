package com.nagne.domain.user.entity;

import lombok.Getter;

@Getter
public enum UserRole {
  ADMIN("ROLE_ADMIN","관리자"),
  USER("ROLE_USER","유저");

  private final String roleName;
  private final String description;

  UserRole(String role, String description){
    this.roleName = role;
    this.description = description;
  }
}
