package com.shopping.dto.signin;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequestDto {

  @NotBlank(message = "username.required")
  @ApiModelProperty(value = "Username", example = "user@shopping.com")
  private String username;

  @NotBlank(message = "password.required")
  @ApiModelProperty(value = "Password", example = "123456")
  private String password;

}
