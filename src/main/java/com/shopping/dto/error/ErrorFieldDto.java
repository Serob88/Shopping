package com.shopping.dto.error;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@ApiModel("ErrorFieldDto")
public class ErrorFieldDto {

  @ApiModelProperty(value = "Error field")
  private String field;

  @ApiModelProperty(value = "Error field default message")
  private String defaultMessage;

}
