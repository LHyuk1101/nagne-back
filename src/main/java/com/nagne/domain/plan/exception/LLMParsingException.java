package com.nagne.domain.plan.exception;

public class LLMParsingException extends RuntimeException {

  public LLMParsingException(String message, Throwable cause) {
    super(message, cause);
  }

  public LLMParsingException(String message) {
    super(message);
  }
}
