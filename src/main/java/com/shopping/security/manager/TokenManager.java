package com.shopping.security.manager;

import com.shopping.entity.User;
import com.shopping.security.pojo.AccessTokenClaims;
import com.shopping.config.proportis.JwtConfigProperties;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenManager {

  private static final String ROLE_PREFIX = "ROLE_";

  private final JwtConfigProperties jwtConfigProperties;
  private final JwtManager jwtManager;

  /**
   * Generate access token based on provided user id and authorities
   *
   * @param user {@link User}
   * @return jwt access token
   */
  public String generateAccessToken(final User user) {
    final AccessTokenClaims claims = AccessTokenClaims.builder()
        .sub(jwtConfigProperties.getAccessToken().getSubject())
        .iss(jwtConfigProperties.getAccessToken().getIssuer())
        .exp(Instant.now().plus(jwtConfigProperties.getAccessToken().getValidity(), ChronoUnit.MINUTES))
        .authorities(user.getRoles().stream().map(role -> StringUtils.join(ROLE_PREFIX, role.getType()))
            .collect(Collectors.toSet()))
        .username(user.getName())
        .userId(user.getId())
        .build();

    return jwtManager.generateTokenFromMappedClaims(claims, jwtConfigProperties.getSignatureKey(),
        jwtConfigProperties.getSignatureAlgorithm());
  }

  /**
   * Get refresh token expiration date time based on device type
   *
   * @return expiration date time
   */
  private LocalDateTime getRefreshTokenExpiresIn() {
    return LocalDateTime.now().plus(jwtConfigProperties.getRefreshToken().getValidity(), ChronoUnit.DAYS);
  }

}
