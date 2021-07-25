package com.shopping.dto.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel("ErrorResponseDto")
public class ErrorResponseDto {

  @ApiModelProperty(value = "System error code", required = true)
  private String errorCode;

  @ApiModelProperty(value = "Error message")
  private String errorMessage;

  @ApiModelProperty(value = "HTTP Status")
  private int statusCode;

  @ApiModelProperty(value = "List of errors")
  private List<ErrorFieldDto> errors;

}
