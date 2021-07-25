package com.shopping.service.impl;

import static com.shopping.entity.enums.RoleType.USER;

import com.shopping.config.proportis.JwtConfigProperties;
import com.shopping.dto.signin.SignInRequestDto;
import com.shopping.dto.signin.SignInResponseDto;
import com.shopping.dto.signup.SignUpRequestDto;
import com.shopping.dto.signup.SignUpResponseDto;
import com.shopping.entity.Role;
import com.shopping.entity.User;
import com.shopping.exception.error.ErrorCode;
import com.shopping.exception.UserException;
import com.shopping.security.manager.TokenManager;
import com.shopping.repository.RoleRepository;
import com.shopping.repository.UserRepository;
import com.shopping.service.AuthService;
import com.shopping.service.RefreshTokenService;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  private final TokenManager tokenManager;
  private final RefreshTokenService refreshTokenService;
  private final MapperFacade mapper;

  private final JwtConfigProperties jwtConfigProperties;

  @Override
  @Transactional
  public SignUpResponseDto signUp(SignUpRequestDto request) {
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
      log.warn("Email already exists: {}", request.getEmail());

      throw new UserException(ErrorCode.EMAIL_ALREADY_EXIST);
    }
    final Role role = new Role();
    role.setType(USER);

    log.info("trying to saved role: {}", role);
    roleRepository.save(role);

    final User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPassword(DigestUtils.md5DigestAsHex(request.getPassword().getBytes(StandardCharsets.UTF_8)));
    user.setBirthday(request.getBirthday());
    user.setRoles(Collections.singletonList(role));

    log.info("trying to saved user: {}", user);
    userRepository.save(user);

    return mapper.map(user, SignUpResponseDto.class);
  }

  @Override
  @Transactional
  public SignInResponseDto signIn(SignInRequestDto request) {
    final User user = userRepository.findByEmail(request.getUsername())
        .orElseThrow(() -> new UserException(ErrorCode.SIGN_IN_BAD_CREDENTIALS));

    if (user.isBlock()) {
      throw new UserException(ErrorCode.USER_IS_BLOCKED);
    }

    final String pwdEncrypted = DigestUtils.md5DigestAsHex(request.getPassword().getBytes(StandardCharsets.UTF_8));

    if (!pwdEncrypted.equals(user.getPassword())) {
      log.error("Signin failed for the username: {}", request.getUsername());
      throw new UserException(ErrorCode.SIGN_IN_BAD_CREDENTIALS);
    }

    return buildSignInResponseDto(user);
  }

  private SignInResponseDto buildSignInResponseDto(final User user) {
    final String accessToken = tokenManager.generateAccessToken(user);
    final String refreshToken = refreshTokenService.retrieveActiveRefreshToken(user.getId());

    return SignInResponseDto.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .tokenType(jwtConfigProperties.getAuthorizationPrefix())
        .expiresIn(jwtConfigProperties.getAccessToken().getValidity() * 60L)
        .build();
  }

}
