package com.shopping.service;

import com.shopping.dto.signin.SignInRequestDto;
import com.shopping.dto.signin.SignInResponseDto;
import com.shopping.dto.signup.SignUpRequestDto;
import com.shopping.dto.signup.SignUpResponseDto;

public interface AuthService {

  /**
   * Sign up user.
   *
   * @param request {@link SignUpRequestDto}
   * @return {@link SignUpResponseDto}
   */
  SignUpResponseDto signUp(SignUpRequestDto request);

  /**
   * Sing in user.
   *
   * @param request {@link SignInRequestDto}
   * @return {@link SignInResponseDto}
   */
  SignInResponseDto signIn(SignInRequestDto request);

}
