package com.shopping.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel("ProductResponseDto")
public class ProductResponseDto {

  @ApiModelProperty(value = "Id")
  private Long id;

  @ApiModelProperty(value = "Name")
  private String name;

  @ApiModelProperty(value = "Price")
  private Double price;

  @ApiModelProperty(value = "Category")
  private String category;

  @ApiModelProperty(value = "Rate")
  private Double rate;

  @ApiModelProperty(value = "Product reviews")
  private List<ReviewResponseDto> reviews;
}
