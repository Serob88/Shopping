package com.shopping.dto.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@ApiModel("ReviewResponseDto")
public class ReviewResponseDto {

  @ApiModelProperty(value = "Id")
  private Long id;

  @ApiModelProperty(value = "comment")
  private String comment;

  @ApiModelProperty(value = "rate")
  private int rate;

  @ApiModelProperty(value = "date")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  protected LocalDateTime createdDate;

}
