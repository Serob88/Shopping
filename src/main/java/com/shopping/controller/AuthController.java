package com.shopping.controller;

import com.shopping.api.AuthApi;
import com.shopping.dto.refreshtoken.RefreshTokenRequestDto;
import com.shopping.dto.refreshtoken.RefreshTokenResponseDto;
import com.shopping.dto.signin.SignInRequestDto;
import com.shopping.dto.signin.SignInResponseDto;
import com.shopping.dto.signup.SignUpRequestDto;
import com.shopping.dto.signup.SignUpResponseDto;
import com.shopping.service.AuthService;
import com.shopping.service.RefreshTokenService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/auth")
public class AuthController implements AuthApi {

  private final AuthService authService;
  private final RefreshTokenService refreshTokenService;

  @Override
  @PostMapping(value = "/signIn")
  public ResponseEntity<SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto request) {
    log.info("Trying to sing in user with credentials: {}", request);

    return ResponseEntity.ok(authService.signIn(request));
  }

  @Override
  @PostMapping(value = "/signUp")
  public ResponseEntity<SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto request) {
    log.info("Trying to sing in user with credentials: {}", request);

    return ResponseEntity.ok(authService.signUp(request));
  }

  @Override
  @PostMapping(value = "/refreshToken")
  public ResponseEntity<RefreshTokenResponseDto> refreshToken(RefreshTokenRequestDto request) {
    log.info("Trying to refresh JWT token: {}", request);

    return ResponseEntity.ok(refreshTokenService.refreshToken(request));
  }

}
