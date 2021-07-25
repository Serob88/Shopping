package com.shopping.exception.error;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorInfo {

  private String errorCode;
  private String message;
  private List<ErrorParam> errorParams;

}
