package com.shopping.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "CommentRequestDto")
public class CommentRequestDto {

  @ApiModelProperty(value = "The comment text")
  private String text;
}
