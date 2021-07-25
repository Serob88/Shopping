package com.shopping.exception;

import com.shopping.exception.error.ErrorCode;

public class UserException extends BaseException {

  public UserException(final ErrorCode errorCode) {
    super(errorCode);
  }

}
