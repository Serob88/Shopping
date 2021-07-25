package com.shopping.api;

import com.shopping.dto.user.UserDetailsDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

@Api(tags = "user")
public interface UserApi {

  @ApiOperation(value = "Find user by id")
  @ApiResponses({
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 403, message = "User blocked"),
      @ApiResponse(code = 500, message = "Internal Server Error")
  })
  ResponseEntity<UserDetailsDto> getUserById(@ApiParam(name = "id", required = true) Long id);

  @ApiOperation(value = "Block user by id")
  @ApiResponses({
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 403, message = "User blocked"),
      @ApiResponse(code = 500, message = "Internal Server Error")
  })
  ResponseEntity<Void> blocked(@ApiParam(name = "id", required = true) Long id);

  @ApiOperation(value = "Unblock user by id")
  @ApiResponses({
      @ApiResponse(code = 200, message = "OK"),
      @ApiResponse(code = 500, message = "Internal Server Error")
  })
  ResponseEntity<Void> unBlock(@ApiParam(name = "id", required = true) Long id);
}
