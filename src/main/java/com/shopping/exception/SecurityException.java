package com.shopping.exception;

import com.shopping.exception.error.ErrorCode;

public class SecurityException extends BaseException {

  public SecurityException(ErrorCode errorCode) {
    super(errorCode);
  }

}
