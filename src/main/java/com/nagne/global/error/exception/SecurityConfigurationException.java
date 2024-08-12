package com.nagne.global.error.exception;

import com.nagne.global.error.ErrorCode;

public class SecurityConfigurationException extends ApiException {

  public SecurityConfigurationException(String message) {
    super(message, ErrorCode.SECURITY_CONFIGURATION_ERROR);
  }
}
