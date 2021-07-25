package com.shopping.service.impl;

import com.shopping.dto.user.UserDetailsDto;
import com.shopping.entity.User;
import com.shopping.exception.error.ErrorCode;
import com.shopping.exception.UserException;
import com.shopping.repository.UserRepository;
import com.shopping.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final MapperFacade mapper;

  @Override
  @Transactional
  public UserDetailsDto findById(final Long userId) {

    return mapper.map(findUser(userId), UserDetailsDto.class);
  }


  @Override
  @Transactional
  public void blocked(final Long userId) {
    User user = findUser(userId);
    user.setBlock(true);

    log.info("trying to saved user: {}", user);
    userRepository.save(user);
  }

  @Override
  @Transactional
  public void unBlocked(final Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

    user.setBlock(false);

    log.info("trying to saved user: {}", user);
    userRepository.save(user);
  }

  private User findUser(final Long userId) {
    final User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

    if (user.isBlock()) {
      throw new UserException(ErrorCode.USER_IS_BLOCKED);
    }

    return user;
  }
}
