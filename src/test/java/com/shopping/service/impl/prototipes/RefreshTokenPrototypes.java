package com.shopping.service.impl.prototipes;

import com.shopping.entity.RefreshToken;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RefreshTokenPrototypes extends Prototype {

  public static RefreshToken buildRefreshToken(boolean active, LocalDateTime expirationDate) {
    final RefreshToken refreshToken = new RefreshToken();
    refreshToken.setActive(active);
    refreshToken.setToken(UUID.randomUUID().toString());
    refreshToken.setExpirationDate(expirationDate);

    return refreshToken;
  }
}
