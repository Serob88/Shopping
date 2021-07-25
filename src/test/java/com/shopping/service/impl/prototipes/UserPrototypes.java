package com.shopping.service.impl.prototipes;

import com.shopping.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserPrototypes extends Prototype {

  public static User buildUser(final String username, final String email) {
    final User user = new User();
    user.setName(username);
    user.setEmail(email);

    return user;
  }

  public static User buildUser(final String username, final String email, final boolean isBlocked) {
    final User user = buildUser(username, email);
    user.setBlock(isBlocked);

    return user;
  }

  public static User buildUser(final String username, final String email, final String password,
      final boolean isBlocked) {
    final User user = buildUser(username, email, isBlocked);
    user.setPassword(password);

    return user;
  }
}
