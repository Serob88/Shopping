package com.shopping.dto.user;

import com.shopping.entity.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("UserDetailsDto")
public class UserDetailsDto extends UserResponseDto {

  @ApiModelProperty(value = "User roles")
  private List<Role> roles;
}
