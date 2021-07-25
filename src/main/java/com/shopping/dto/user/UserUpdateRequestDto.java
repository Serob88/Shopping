package com.shopping.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("UserUpdateRequestDto")
public class UserUpdateRequestDto {

  @ApiModelProperty(value = "Name")
  private String name;

  @ApiModelProperty(value = "Birthday")
  private Date birthday;

}
