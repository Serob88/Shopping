package com.shopping.dto.signup;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("SignUpResponseDto")
public class SignUpResponseDto {

  @ApiModelProperty(value = "Id")
  private Long id;

  @ApiModelProperty(value = "Name")
  private String name;

  @ApiModelProperty(value = "Email address", example = "user@shopping.com")
  private String email;

  @ApiModelProperty(value = "Birthday")
  private String birthday;

}
