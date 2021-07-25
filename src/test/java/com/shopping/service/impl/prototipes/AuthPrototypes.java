package com.shopping.service.impl.prototipes;

import com.shopping.dto.signin.SignInRequestDto;
import com.shopping.dto.signup.SignUpRequestDto;
import java.sql.Date;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthPrototypes extends Prototype {

  public static SignUpRequestDto buildSignUpRequestDto(final String email, final String username, final String password,
      final Date birthday) {
    final SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
    signUpRequestDto.setEmail(email);
    signUpRequestDto.setName(username);
    signUpRequestDto.setPassword(password);
    signUpRequestDto.setBirthday(birthday);

    return signUpRequestDto;
  }

  public static SignInRequestDto buildSignInRequestDto(final String username, final String password) {
    final SignInRequestDto signInRequestDto = new SignInRequestDto();
    signInRequestDto.setUsername(username);
    signInRequestDto.setPassword(password);

    return signInRequestDto;
  }
}
