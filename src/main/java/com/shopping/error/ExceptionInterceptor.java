package com.shopping.error;

import static com.shopping.exception.error.ErrorCode.VALIDATION_REQUIRED;

import com.shopping.dto.error.ErrorFieldDto;
import com.shopping.dto.error.ErrorResponseDto;
import com.shopping.exception.BaseException;
import com.shopping.exception.error.ErrorCode;
import com.shopping.exception.error.ErrorInfo;
import com.shopping.exception.error.ErrorParam;
import com.shopping.exception.SecurityException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class ExceptionInterceptor {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    log.error("Received method argument not valid exception. Error {}", ex.getMessage());

    // Collect validation error for response
    final List<ErrorFieldDto> errorParams = ex.getBindingResult().getAllErrors()
        .stream()
        .map(error -> {
          final String fieldName = ((FieldError) error).getField();
          final String errorMessage = error.getDefaultMessage();

          return ErrorFieldDto.builder()
              .field(fieldName)
              .defaultMessage(errorMessage)
              .build();
        }).collect(Collectors.toList());

    final ErrorResponseDto errorResponse = ErrorResponseDto.builder()
        .errorCode(VALIDATION_REQUIRED.name())
        .errors(errorParams)
        .build();

    return ResponseEntity.status(VALIDATION_REQUIRED.getStatus()).body(errorResponse);
  }

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ErrorInfo> handleBaseException(final BaseException ex, WebRequest request) {
    log.error("Received {} exception with error code {}", ex.getClass().getSimpleName(), ex.getErrorCode());

    final ErrorCode errorCode = ex.getErrorCode();

    return handleErrorResponse(errorCode.name(), ex.getMessage(), errorCode.getStatus(), ex.getErrorParams());
  }

  private ResponseEntity<ErrorInfo> handleErrorResponse(String errorCode, String errorMessage, HttpStatus httpStatus,
      List<ErrorParam> errorParams) {

    final ErrorInfo errorInfo = ErrorInfo.builder()
        .errorCode(errorCode)
        .message(errorMessage)
        .errorParams(errorParams)
        .build();

    return ResponseEntity.status(httpStatus)
        .contentType(MediaType.APPLICATION_JSON)
        .body(errorInfo);
  }
}
