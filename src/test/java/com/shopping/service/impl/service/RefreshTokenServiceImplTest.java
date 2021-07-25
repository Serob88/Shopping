package com.shopping.service.impl.service;

import static com.shopping.service.impl.prototipes.RefreshTokenPrototypes.buildRefreshToken;
import static com.shopping.service.impl.prototipes.UserPrototypes.buildUser;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shopping.dto.refreshtoken.RefreshTokenRequestDto;
import com.shopping.dto.refreshtoken.RefreshTokenResponseDto;
import com.shopping.entity.RefreshToken;
import com.shopping.entity.User;
import com.shopping.exception.SecurityException;
import com.shopping.exception.error.ErrorCode;
import com.shopping.security.manager.TokenManager;
import com.shopping.config.proportis.JwtConfigProperties;
import com.shopping.config.proportis.JwtConfigProperties.RefreshTokenConfigProperties;
import com.shopping.repository.RefreshTokenRepository;
import com.shopping.repository.UserRepository;
import com.shopping.service.impl.RefreshTokenServiceImpl;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RefreshTokenServiceImplTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private RefreshTokenRepository refreshTokenRepository;
  @Mock
  private TokenManager tokenManager;
  @Mock
  private JwtConfigProperties jwtConfigProperties;

  @InjectMocks
  private RefreshTokenServiceImpl refreshTokenService;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void refreshToken_Success() {
    // GIVEN
    final RefreshTokenRequestDto request = new RefreshTokenRequestDto();
    request.setRefreshToken(UUID.randomUUID().toString());

    final RefreshToken refreshToken = new RefreshToken();
    refreshToken.setActive(true);
    refreshToken.setExpirationDate(LocalDateTime.now().plusMinutes(10));

    when(refreshTokenRepository.findByToken(request.getRefreshToken())).thenReturn(Optional.of(refreshToken));
    when(tokenManager.generateAccessToken(any())).thenReturn(UUID.randomUUID().toString());

    // WHEN
    final RefreshTokenResponseDto response = refreshTokenService.refreshToken(request);

    // THEN
    assertThat(response).isNotNull();
    assertThat(response.getAccessToken()).isNotNull();
  }

  @Test
  public void refreshToken_REFRESH_TOKEN_NOT_FOUND() {
    // GIVEN
    final RefreshTokenRequestDto request = new RefreshTokenRequestDto();
    request.setRefreshToken(UUID.randomUUID().toString());

    when(refreshTokenRepository.findByToken(request.getRefreshToken())).thenReturn(Optional.empty());

    // WHEN THEN
    assertThatThrownBy(() -> refreshTokenService.refreshToken(request))
        .isInstanceOf(SecurityException.class)
        .hasFieldOrPropertyWithValue("errorCode", ErrorCode.REFRESH_TOKEN_NOT_FOUND);
  }

  @Test
  public void refreshToken_REFRESH_TOKEN_BLOCKED() {
    // GIVEN
    final RefreshTokenRequestDto request = new RefreshTokenRequestDto();
    request.setRefreshToken(UUID.randomUUID().toString());

    final RefreshToken refreshToken = new RefreshToken();
    refreshToken.setActive(false);

    when(refreshTokenRepository.findByToken(request.getRefreshToken())).thenReturn(Optional.of(refreshToken));

    // WHEN THEN
    assertThatThrownBy(() -> refreshTokenService.refreshToken(request))
        .isInstanceOf(SecurityException.class)
        .hasFieldOrPropertyWithValue("errorCode", ErrorCode.REFRESH_TOKEN_BLOCKED);
  }

  @Test
  public void refreshToken_REFRESH_TOKEN_EXPIRED() {
    // GIVEN
    final RefreshTokenRequestDto request = new RefreshTokenRequestDto();
    request.setRefreshToken(UUID.randomUUID().toString());

    final RefreshToken refreshToken = buildRefreshToken(true, LocalDateTime.now().minusDays(1));

    when(refreshTokenRepository.findByToken(request.getRefreshToken())).thenReturn(Optional.of(refreshToken));

    // WHEN THEN
    assertThatThrownBy(() -> refreshTokenService.refreshToken(request))
        .isInstanceOf(SecurityException.class)
        .hasFieldOrPropertyWithValue("errorCode", ErrorCode.REFRESH_TOKEN_EXPIRED);
  }

  @Test
  public void retrieveActiveRefreshToken_Success() {
    // GIVEN
    final User secUserEntity = buildUser("admin", "admin@gmail.com", false);
    secUserEntity.setId(1L);

    final RefreshToken refreshToken = buildRefreshToken(true, LocalDateTime.now().plusDays(1));
    refreshToken.setUser(secUserEntity);

    when(refreshTokenRepository.findFirstByUserIdAndActive(secUserEntity.getId(), true))
        .thenReturn(Optional.of(refreshToken));
    when(refreshTokenRepository.save(refreshToken)).then(returnsFirstArg());

    // WHEN
    final String response = refreshTokenService.retrieveActiveRefreshToken(secUserEntity.getId());

    // THEN
    assertThat(response).isNotNull();

    verify(refreshTokenRepository, never()).invalidateByUserId(secUserEntity.getId());
  }

  @Test
  public void issueNewRefreshTokenWhenUserIsDifferent() {
    // GIVEN
    final User secUserEntity = buildUser("admin", "admin@gmail.com", false);
    secUserEntity.setId(1L);

    final RefreshToken refreshToken = buildRefreshToken(true, LocalDateTime.now().plusDays(1));
    refreshToken.setUser(secUserEntity);

    when(refreshTokenRepository.findFirstByUserIdAndActive(any(), eq(true)))
        .thenReturn(Optional.of(refreshToken));
    when(refreshTokenRepository.save(any())).then(returnsFirstArg());
    when(jwtConfigProperties.getRefreshToken()).thenReturn(mock(RefreshTokenConfigProperties.class));

    // WHEN
    final String response = refreshTokenService.retrieveActiveRefreshToken(2L);

    // THEN
    assertThat(response).isNotNull();

    verify(refreshTokenRepository, times(1)).invalidateByUserId(any());
  }

  @Test
  public void issueNewRefreshTokenWhenExpired() {
    // GIVEN
    final User secUserEntity = buildUser("admin", "admin@gmail.com", false);
    secUserEntity.setId(1L);

    final RefreshToken refreshToken = buildRefreshToken(true, LocalDateTime.now().minusDays(1));
    refreshToken.setUser(secUserEntity);

    when(refreshTokenRepository.findFirstByUserIdAndActive(any(), eq(true)))
        .thenReturn(Optional.of(refreshToken));
    when(refreshTokenRepository.save(any())).then(returnsFirstArg());
    when(jwtConfigProperties.getRefreshToken()).thenReturn(mock(RefreshTokenConfigProperties.class));

    // WHEN
    final String response = refreshTokenService.retrieveActiveRefreshToken(2L);

    // THEN
    assertThat(response).isNotNull();

    verify(refreshTokenRepository, times(1)).invalidateByUserId(any());
  }

}
