package com.shopping.exception;

import com.shopping.exception.error.ErrorCode;

public class ValidationException extends BaseException {

  public ValidationException(ErrorCode errorCode) {
    super(errorCode);
  }

  public ValidationException(ErrorCode errorCode, Object[] errorParams) {
    super(errorCode, errorParams);
  }
}

