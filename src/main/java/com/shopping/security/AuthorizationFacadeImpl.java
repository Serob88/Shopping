package com.shopping.security;

import com.shopping.dto.user.UserResponseDto;
import com.shopping.entity.User;
import com.shopping.exception.error.ErrorCode;
import com.shopping.exception.UserException;
import com.shopping.security.pojo.ContextUser;
import com.shopping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationFacadeImpl implements AuthorizationFacade {

  private final UserRepository userRepository;
  private final SecurityUtil securityUtil;
  private final MapperFacade mapper;

  @Override
  public ContextUser currentUser() {
    return securityUtil.getCurrentUser();
  }

  @Override
  public Long getCurrentUserId() {
    return securityUtil.getCurrentUserId();
  }

  @Override
  public UserResponseDto getCurrentUser() {
    return getCurrentUser(false);
  }

  @Override
  public UserResponseDto getCurrentUser(boolean validate) {
    ContextUser contextUser = currentUser();
    if (contextUser == null) {
      if (validate) {
        throw new UserException(ErrorCode.USER_NOT_FOUND);
      }
      return null;
    } else {
      User userEntity = userRepository.findById(contextUser.getUserId())
          .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

      return mapper.map(userEntity, UserResponseDto.class);
    }
  }

  @Override
  public User getUser() {
    ContextUser contextUser = currentUser();

    if (contextUser == null) {
      throw new UserException(ErrorCode.USER_UNAUTHORIZED);
    }

    return userRepository.findById(contextUser.getUserId())
        .orElseGet(() -> {
          log.warn("User not found by id:[{}]", contextUser.getUserId());

          throw new UserException(ErrorCode.USER_NOT_FOUND);
        });
  }

}
