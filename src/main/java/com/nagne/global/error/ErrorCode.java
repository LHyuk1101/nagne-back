package com.nagne.global.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.boot.logging.LogLevel;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
  
  // Common
  INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value", LogLevel.ERROR),
  METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value", LogLevel.ERROR),
  ENTITY_NOT_FOUND(400, "C003", " Entity Not Found", LogLevel.ERROR),
  INTERNAL_SERVER_ERROR(500, "C004", "Server Error", LogLevel.ERROR),
  INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value", LogLevel.ERROR),
  HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied", LogLevel.ERROR),
  NO_HANDLER_FOUND(404, "C007", "No Handler found", LogLevel.ERROR),
  NO_RESOURCE_FOUND(404, "C008", "No Resource found", LogLevel.ERROR),
  
  // USER
  EMAIL_ALREADY_REGISTERED(400, "U001", "Email already registered", LogLevel.ERROR),
  OAUTH2_CREATE_USER_ERROR(500, "U002", "Error occurred while creating OAuth2 user", LogLevel.ERROR),

  // Secure
  SECURITY_CONFIGURATION_ERROR(500, "S001", "Security Configuration Error", LogLevel.ERROR),
  USER_UNAUTHORIZED(401, "S002", "Authentication is required to access this resource", LogLevel.ERROR ),
  AUTHENTICATION_FAILURE(401, "S003", "Authentication Failure", LogLevel.ERROR),
  INVALID_REFRESH_TOKEN(401, "S004", "Invalid refresh token", LogLevel.ERROR),
  FORBIDDEN(403, "S005", "Access denied: Insufficient permissions", LogLevel.ERROR),

  // Place
  PLACE_FOUND_NOT_ERROR(404, "P001", "Place Entity Not Found", LogLevel.ERROR),
  ;
  
  private final String code;
  private final String message;
  private final LogLevel logLevel;
  private final int status;
  
  ErrorCode(final int status, final String code, final String message, LogLevel logLevel) {
    this.status = status;
    this.message = message;
    this.code = code;
    this.logLevel = logLevel;
  }
  
  public String getMessage() {
    return this.message;
  }
  
  public String getCode() {
    return code;
  }
  
  public int getStatus() {
    return status;
  }
  
  public LogLevel getLogLevel() {
    return logLevel;
  }
  
}