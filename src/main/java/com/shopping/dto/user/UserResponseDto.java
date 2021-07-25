package com.shopping.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("UserResponseDto")
public class UserResponseDto {

  @ApiModelProperty(value = "Id")
  private Long id;

  @ApiModelProperty(value = "Name")
  private String name;

  @ApiModelProperty(value = "Email address", example = "user@shopping.com")
  private String email;

  @ApiModelProperty(value = "Birthday")
  private String birthday;

  @ApiModelProperty(value = "If user is blocked")
  private boolean isBlocked;
}
