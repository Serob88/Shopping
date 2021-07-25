package com.shopping.dto.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "CommentRequestDto")
public class CommentRequestDto {

  @ApiModelProperty(value = "The review comment")
  private String text;

  @Max(5)
  @Min(1)
  @ApiModelProperty(value = "The review rate")
  private Integer rate;
}
