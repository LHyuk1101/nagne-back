package com.nagne.domain.user.entity;

import lombok.Getter;

@Getter
public enum OauthProvider {
  INSTARGRAM("INSTARGRAM","인스타그램"),
  GOOGLE("GOOGLE","구글");

  private final String providerName;
  private final String description;

  OauthProvider(String providerName, String description){
    this.providerName = providerName;
    this.description = description;
  }
}
