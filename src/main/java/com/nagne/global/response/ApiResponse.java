package com.nagne.global.response;

import com.nagne.global.error.ErrorCode;
import com.nagne.global.error.ErrorMessage;

public class ApiResponse<T> {
  
  private final ResultType result;
  
  private final ErrorMessage message;
  
  private final T items;
  
  private ApiResponse(ResultType result, ErrorMessage message, T items) {
    this.result = result;
    this.message = message;
    this.items = items;
  }
  
  public static ApiResponse<?> success() {
    return new ApiResponse<>(ResultType.SUCCESS, null, null);
  }
  
  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<T>(ResultType.SUCCESS, null, data);
  }
  
  public static ApiResponse<?> error(ErrorCode code) {
    return new ApiResponse<>(ResultType.ERROR, new ErrorMessage(code), null);
  }
  
  public static ApiResponse<?> error(ErrorCode code, Object errorData) {
    return new ApiResponse<>(ResultType.ERROR, new ErrorMessage(code, errorData), null);
  }
  
  public ResultType getResult() {
    return result;
  }
  
  public ErrorMessage getMessage() {
    return message;
  }
  
  public T getItems() {
    return items;
  }
}
