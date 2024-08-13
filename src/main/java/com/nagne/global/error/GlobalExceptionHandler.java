package com.nagne.global.error;

import com.nagne.global.error.exception.ApiException;
import com.nagne.global.response.ApiResponse;
import java.nio.file.AccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  
  /**
   * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다. HttpMessageConverter 에서 등록한
   * HttpMessageConverter binding 못할경우 발생 주로 @RequestBody, @RequestPart 어노테이션에서 발생
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
    MethodArgumentNotValidException e) {
    log.error("handleMethodArgumentNotValidException", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE,
      e.getBindingResult());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
  
  /**
   * @ModelAttribut 으로 binding error 발생시 BindException 발생한다. ref
   * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
   */
  @ExceptionHandler(BindException.class)
  protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
    log.error("handleBindException", e);
    final ErrorResponse response = ErrorResponse.of(
      ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
  
  /**
   * enum type 일치하지 않아 binding 못할 경우 발생 주로 @RequestParam enum으로 binding 못했을 경우 발생
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
    MethodArgumentTypeMismatchException e) {
    log.error("handleMethodArgumentTypeMismatchException", e);
    final ErrorResponse response = ErrorResponse.of(e);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }
  
  /**
   * 지원하지 않은 HTTP method 호출 할 경우 발생
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
    HttpRequestMethodNotSupportedException e) {
    log.error("handleHttpRequestMethodNotSupportedException", e);
    final ErrorResponse response = ErrorResponse.of(
      ErrorCode.METHOD_NOT_ALLOWED);
    return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
  }
  
  /**
   * 존재 하지 않는 URL 주소를 입력 했을 때 발생함
   */
  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException e) {
    log.error("handleNoHandlerFoundException", e);
    final ErrorResponse response = ErrorResponse.of(
      ErrorCode.NO_HANDLER_FOUND);
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }
  
  /**
   * 리소스를 찾을 수 없는 경우 발생함
   */
  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException e) {
    log.error("handleNoResourceFoundException", e);
    final ErrorResponse response = ErrorResponse.of(
      ErrorCode.NO_RESOURCE_FOUND);
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }
  
  /**
   * Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생합
   */
  @ExceptionHandler(AccessDeniedException.class)
  protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
    log.error("handleAccessDeniedException", e);
    final ErrorResponse response = ErrorResponse.of(
      ErrorCode.HANDLE_ACCESS_DENIED);
    return new ResponseEntity<>(response, HttpStatus.valueOf(
      ErrorCode.HANDLE_ACCESS_DENIED.getStatus()));
  }
  
  /**
   * 각 도메인별 비지니스 CustomException 동작하는 메서드 입니다!.
   */
  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiResponse<?>> handleApiException(ApiException e) {
    final ErrorCode errorCode = e.getErrorCode();
    
    switch (errorCode.getLogLevel()) {
      case ERROR -> log.error(errorCode.getMessage());
      case WARN -> log.warn(errorCode.getMessage());
      default -> log.info(errorCode.getMessage());
    }
    return new ResponseEntity<>(ApiResponse.error(e.getErrorCode()),
      HttpStatus.valueOf(e.getErrorCode().getStatus()));
  }
  
  /**
   * 개발자가 캐치하지 못해서 나오는 원인 불명의 에러 또는 의도된 checkedException 처리가 되어야합니다.
   * <p>
   * 관련 자료가 궁금하시다면 이펙티브 자바, 쉽게 정리하기 - item 71. 필요 없는 검사 예외(checked error) 사용은 피하라 를 확인해주세요.
   */
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ErrorResponse> handleException(Exception e) {
    log.error("ServerInternalException", e);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
