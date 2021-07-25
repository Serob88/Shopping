package com.shopping.service.impl;


import com.shopping.dto.refreshtoken.RefreshTokenRequestDto;
import com.shopping.dto.refreshtoken.RefreshTokenResponseDto;
import com.shopping.entity.RefreshToken;
import com.shopping.exception.SecurityException;
import com.shopping.exception.error.ErrorCode;
import com.shopping.security.manager.TokenManager;
import com.shopping.config.proportis.JwtConfigProperties;
import com.shopping.repository.RefreshTokenRepository;
import com.shopping.repository.UserRepository;
import com.shopping.service.RefreshTokenService;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

  // Repositories
  private final UserRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;

  // Other
  private final TokenManager tokenManager;
  private final JwtConfigProperties jwtConfigProperties;

  @Override
  @Transactional
  public RefreshTokenResponseDto refreshToken(final RefreshTokenRequestDto request) {
    final RefreshToken refreshToken = refreshTokenRepository.findByToken(request.getRefreshToken())
        .orElseThrow(() -> new SecurityException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

    if (!refreshToken.isActive()) {
      log.info("Refresh token blocked with prefix: {}", StringUtils.left(refreshToken.getToken(), 10));
      throw new SecurityException(ErrorCode.REFRESH_TOKEN_BLOCKED);
    }

    if (LocalDateTime.now().isAfter(refreshToken.getExpirationDate())) {
      log.info("Refresh token expired with prefix: {}", StringUtils.left(refreshToken.getToken(), 10));
      throw new SecurityException(ErrorCode.REFRESH_TOKEN_EXPIRED);
    }

    final String accessToken = tokenManager.generateAccessToken(refreshToken.getUser());

    return RefreshTokenResponseDto.builder()
        .accessToken(accessToken)
        .build();
  }

  @Override
  @Transactional
  public String retrieveActiveRefreshToken(Long userId) {
    RefreshToken refreshToken = refreshTokenRepository.findFirstByUserIdAndActive(userId, true)
        .orElse(null);

    boolean newRefreshTokenRequired = false;

    if (refreshToken != null) {
      // If user trying to log in from another device invalidate it and generate new one
      if (!refreshToken.getUser().getId().equals(userId)) {
        refreshTokenRepository.invalidateByUserId(refreshToken.getUser().getId());

        newRefreshTokenRequired = true;
      } else if (LocalDateTime.now().isAfter(refreshToken.getExpirationDate())) {
        // If refresh token expired invalidate it to create new one
        refreshTokenRepository.invalidateByUserId(userId);

        newRefreshTokenRequired = true;
      }
    } else {
      // If user logging in first time, refresh token creation is required
      newRefreshTokenRequired = true;
    }

    if (newRefreshTokenRequired) {
      refreshToken = buildRefreshTokenAndAssignToUser(userId);
    }

    return refreshTokenRepository.save(refreshToken).getToken();
  }

  private RefreshToken buildRefreshTokenAndAssignToUser(final Long userId) {
    final RefreshToken refreshToken = new RefreshToken();
    refreshToken.setToken(UUID.randomUUID().toString().replace("-", ""));
    refreshToken.setActive(true);
    refreshToken.setExpirationDate(LocalDateTime.now().plus(jwtConfigProperties.getRefreshToken().getValidity(), ChronoUnit.DAYS));
    refreshToken.setUser(userRepository.getOne(userId));

    return refreshToken;
  }

}
