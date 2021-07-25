package com.shopping.api;

import com.shopping.dto.product.ProductRequestDto;
import com.shopping.dto.product.ProductResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

@Api(tags = "product")
public interface ProductApi {

  @ApiOperation(value = "Creat product")
  @ApiResponses({
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 401, message = "Bad credentials"),
      @ApiResponse(code = 500, message = "Internal Server Error")
  })
  ResponseEntity<ProductResponseDto> creat(@ApiParam("Creat product request") ProductRequestDto request);

  @ApiOperation(value = "Find product by id")
  @ApiResponses({
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 404, message = "product not found"),
      @ApiResponse(code = 500, message = "Internal Server Error")
  })
  ResponseEntity<ProductResponseDto> findById(@ApiParam(name = "id", required = true) Long id);

  @ApiOperation(value = "Find products by name")
  @ApiResponses({
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 500, message = "Internal Server Error")
  })
  ResponseEntity<List<ProductResponseDto>> findByName(@ApiParam(name = "name", required = true) String name, Pageable pageable);

  @ApiOperation(value = "Find products by rate")
  @ApiResponses({
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 500, message = "Internal Server Error")
  })
  ResponseEntity<List<ProductResponseDto>> findByRate(@ApiParam(name = "rate", required = true) int rate, Pageable pageable);

  @ApiOperation(value = "Find products by rate")
  @ApiResponses({
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 500, message = "Internal Server Error")
  })
  ResponseEntity<List<ProductResponseDto>> findByPriceRange(@ApiParam(value = "minPrice") Double minPrice,
      @ApiParam(value = "maxPrice") Double maxPrice, Pageable pageable);

  @ApiOperation(value = "Find all products")
  @ApiResponses({
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 500, message = "Internal Server Error")
  })
  ResponseEntity<List<ProductResponseDto>> findAll(Pageable pageable);

  @ApiOperation(value = "Update product by id")
  @ApiResponses({
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 404, message = "product not found"),
      @ApiResponse(code = 500, message = "Internal Server Error")
  })
  ResponseEntity<ProductResponseDto> update(@ApiParam(name = "id", required = true) Long id,
      @ApiParam(name = "request", required = true)ProductRequestDto request);

  @ApiOperation(value = "Remove product by id")
  @ApiResponses({
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 404, message = "product not found"),
      @ApiResponse(code = 500, message = "Internal Server Error")
  })
  ResponseEntity<Void> delete(@ApiParam(name = "id", required = true) Long id);
}
