package com.shopping.dto.signin;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponseDto {

  @ApiModelProperty(value = "The access token that needs to be used on the service to access APIs", required = true)
  private String accessToken;

  @ApiModelProperty(value = "The refresh token that needs to be used to refresh access token", required = true)
  private String refreshToken;

  @ApiModelProperty(value = "Type of token which need to use as Authorization header prefix before access token",
      example = "Bearer", required = true)
  private String tokenType;

  @ApiModelProperty(value = "Access token expiration time in seconds", example = "3600", required = true)
  private Long expiresIn;
}
