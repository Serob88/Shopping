package com.shopping.dto.refreshtoken;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
@ApiModel("RefreshTokenRequestDto")
public class RefreshTokenRequestDto {

  @NotBlank(message = "refreshToken.required")
  @ApiModelProperty(value = "The refresh token for issuing new access token", required = true)
  private String refreshToken;

}
