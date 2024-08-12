package com.nagne.global.error.exception;


import com.nagne.global.error.ErrorCode;

public class EntityNotFoundException extends ApiException {

  public EntityNotFoundException(String message) {
    super(message, ErrorCode.ENTITY_NOT_FOUND);
  }
}
