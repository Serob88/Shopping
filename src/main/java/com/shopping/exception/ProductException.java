package com.shopping.exception;

import com.shopping.exception.error.ErrorCode;

public class ProductException extends BaseException {

  public ProductException(ErrorCode errorCode) {
    super(errorCode);
  }
}
