package com.shopping.service;

import com.shopping.dto.refreshtoken.RefreshTokenRequestDto;
import com.shopping.dto.refreshtoken.RefreshTokenResponseDto;

public interface RefreshTokenService {

  /**
   * Refresh token
   *
   * @param request {@link RefreshTokenRequestDto} which contains refresh token
   * @return refreshed access token
   */
  RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto request);

  /**
   * Retrieve active refresh token for the user
   *
   * @param userId The user id
   * @return refresh token
   */
  String retrieveActiveRefreshToken(Long userId);

}
