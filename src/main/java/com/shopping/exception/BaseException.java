package com.shopping.exception;

import com.shopping.exception.error.ErrorCode;
import com.shopping.exception.error.ErrorParam;
import java.util.List;
import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {

  private static final long serialVersionUID = 8793065153777362189L;

  private final ErrorCode errorCode;
  private final Object[] messageArgs;
  private final List<ErrorParam> errorParams;

  public BaseException(ErrorCode errorCode) {
    this.errorCode = errorCode;
    this.messageArgs = null;
    this.errorParams = null;
  }

  public BaseException(ErrorCode codes, Object[] messageArgs) {
    this.errorCode = codes;
    this.messageArgs = messageArgs;
    this.errorParams = null;
  }

  public BaseException(ErrorCode code, List<ErrorParam> errorParams) {
    this.errorCode = code;
    this.messageArgs = null;
    this.errorParams = errorParams;
  }

}
