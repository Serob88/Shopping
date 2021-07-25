package com.shopping.dto.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@ApiModel("ProductRequestDto")
public class ProductRequestDto {

  @NotBlank(message = "name.required")
  @ApiModelProperty(value = "Name")
  private String name;

  @NotNull(message = "price.required")
  @ApiModelProperty(value = "Price")
  private Double price;

  @NotBlank(message = "category.required")
  @ApiModelProperty(value = "Category")
  private String category;
}
