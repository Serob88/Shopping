package com.shopping.dto.refreshtoken;

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
@ApiModel("RefreshTokenResponseDto")
public class RefreshTokenResponseDto {

  @ApiModelProperty(value = "The access token that needs to be used on the service to access APIs", required = true)
  private String accessToken;

}
