package com.shopping.api;

import com.shopping.dto.product.ReviewRequestDto;
import com.shopping.dto.product.ReviewResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

@Api(tags = "review")
public interface ReviewApi {

  @ApiOperation(value = "Review product by id")
  @ApiResponses({
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 403, message = "User blocked"),
      @ApiResponse(code = 404, message = "product not found"),
      @ApiResponse(code = 500, message = "Internal Server Error")
  })
  ResponseEntity<ReviewResponseDto> review(@ApiParam(value = "id", required = true) Long productId,
      @ApiParam(value = "The comment text", required = true) ReviewRequestDto request);

}
