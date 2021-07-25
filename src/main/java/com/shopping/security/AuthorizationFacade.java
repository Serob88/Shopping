package com.shopping.security;

import com.shopping.dto.user.UserResponseDto;
import com.shopping.entity.User;
import com.shopping.security.pojo.ContextUser;

public interface AuthorizationFacade {

  ContextUser currentUser();

  Long getCurrentUserId();

  UserResponseDto getCurrentUser();

  UserResponseDto getCurrentUser(boolean validate);

  User getUser();

}
