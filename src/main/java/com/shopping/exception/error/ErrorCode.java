package com.shopping.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  /**
   * Common Error Codes
   */
  OK(HttpStatus.OK),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND),
  USER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED),
  EMAIL_ALREADY_EXIST(HttpStatus.PRECONDITION_FAILED),
  USER_IS_BLOCKED(HttpStatus.BAD_REQUEST),
  USER_NO_ACCESS(HttpStatus.BAD_REQUEST),
  SIGN_IN_BAD_CREDENTIALS(HttpStatus.PRECONDITION_FAILED),

  /**
   * Common Error Codes
   */
  PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND),
  VALIDATION_REQUIRED(HttpStatus.BAD_REQUEST),

  /**
   * Security Error Codes
   */
  ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED),
  ACCESS_TOKEN_INVALID(HttpStatus.UNAUTHORIZED),
  REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND),
  REFRESH_TOKEN_BLOCKED(HttpStatus.BAD_REQUEST),
  REFRESH_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST),
  ACCESS_TOKEN_REQUIRED(HttpStatus.BAD_REQUEST),

  MISSING_REQUIRED_FIELDS(HttpStatus.BAD_REQUEST);

  private final HttpStatus status;

}
