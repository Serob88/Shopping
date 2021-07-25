package com.shopping.dto.signup;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Date;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("SignUpRequestDto")
public class SignUpRequestDto {


  @NotBlank(message = "name.required")
  @ApiModelProperty(value = "Name", example = "John Doe")
  private String name;

  @NotBlank(message = "email.required")
  @ApiModelProperty(value = "Email", example = "user@shopping.com")
  private String email;

  @NotBlank(message = "password.required")
  @ApiModelProperty(value = "Password", example = "123456")
  private String password;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @ApiModelProperty(value = "Nurse date of birth", example = "1994-05-07")
  private Date birthday;

}
