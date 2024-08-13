package com.nagne.global.error;

public class ErrorMessage {
  
  private final int code;
  
  private final String message;
  
  private final Object data;
  
  public ErrorMessage(ErrorCode errorCode) {
    this.code = errorCode.getStatus();
    this.message = errorCode.getMessage();
    this.data = null;
  }
  
  public ErrorMessage(ErrorCode errorCode, Object data) {
    this.code = errorCode.getStatus();
    this.message = errorCode.getMessage();
    this.data = data;
  }
  
  public String getMessage() {
    return message;
  }
  
  public int getCode() {
    return code;
  }
  
  public Object getData() {
    return data;
  }
}
