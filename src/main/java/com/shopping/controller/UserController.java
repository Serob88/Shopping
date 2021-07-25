package com.shopping.controller;

import com.shopping.api.UserApi;
import com.shopping.dto.user.UserDetailsDto;
import com.shopping.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController implements UserApi {

  private final UserService userService;

  @Override
  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDetailsDto> getUserById(@PathVariable(value = "id") Long id) {

    return ResponseEntity.ok(userService.findById(id));
  }

  @Override
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping(value = "/block/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> blocked(@PathVariable(value = "id") Long id) {

    userService.blocked(id);

    return ResponseEntity.ok().build();
  }

  @Override
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping(value = "/unBlock/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> unBlock(@PathVariable(value = "id") Long id) {

    userService.unBlocked(id);

    return ResponseEntity.ok().build();
  }
}
