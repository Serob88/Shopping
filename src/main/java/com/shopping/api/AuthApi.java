package com.shopping.api;

import com.shopping.dto.refreshtoken.RefreshTokenRequestDto;
import com.shopping.dto.refreshtoken.RefreshTokenResponseDto;
import com.shopping.dto.signin.SignInRequestDto;
import com.shopping.dto.signin.SignInResponseDto;
import com.shopping.dto.signup.SignUpRequestDto;
import com.shopping.dto.signup.SignUpResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

@Api(tags = "auth")
public interface AuthApi {

  @ApiOperation(value = "User authentication by credentials")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Sign in succes"),
      @ApiResponse(code = 401, message = "Bad credentials"),
      @ApiResponse(code = 403, message = "User blocked"),
      @ApiResponse(code = 500, message = "Internal Server Error")
  })
  ResponseEntity<SignInResponseDto> signIn(@ApiParam("Sign in request") SignInRequestDto request);

  @ApiOperation(value = "Sign up using user credentials")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Sign up success"),
      @ApiResponse(code = 400, message = "Missing or erroneous parameter"),
      @ApiResponse(code = 409, message = "Email already registered"),
      @ApiResponse(code = 500, message = "Internal Server Error")
  })
  ResponseEntity<SignUpResponseDto> signUp(@ApiParam("Sign up request") SignUpRequestDto request);

  @ApiOperation(value = "refresh JWT token")
  @ApiResponses({
      @ApiResponse(code = 200, message = "RefreshToken up success"),
      @ApiResponse(code = 400, message = "Missing or erroneous parameter"),
      @ApiResponse(code = 404, message = "Token not found"),
      @ApiResponse(code = 500, message = "Internal Server Error")
  })
  ResponseEntity<RefreshTokenResponseDto> refreshToken(@ApiParam("Refresh token") RefreshTokenRequestDto request);

}
