package com.nagne.global.error;

public class ErrorMessage {

  private final String code;

  private final String message;

  private final Object data;

  public ErrorMessage(ErrorCode errorCode) {
    this.code = errorCode.getCode();
    this.message = errorCode.getMessage();
    this.data = null;
  }

  public ErrorMessage(ErrorCode errorCode, Object data) {
    this.code = errorCode.getCode();
    this.message = errorCode.getMessage();
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public String getCode() {
    return code;
  }

  public Object getData() {
    return data;
  }
}
