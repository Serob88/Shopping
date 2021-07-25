package com.shopping.service;

import com.shopping.dto.user.UserDetailsDto;
import com.shopping.dto.user.UserResponseDto;
import com.shopping.dto.user.UserUpdateRequestDto;

public interface UserService {

  /**
   * Find user by id.
   *
   * @param userId Long user id
   * @return {@link UserDetailsDto}
   */
  UserDetailsDto findById(Long userId);

  /**
   * Blocked user by id.
   *
   * @param userId Long user id
   */
  void blocked(Long userId);

  /**
   * Unblocked user.
   *
   * @param userId Long user id
   */
  void unBlocked(Long userId);

}
