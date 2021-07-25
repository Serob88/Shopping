package com.shopping.service.impl.service;

import static com.shopping.service.impl.prototipes.UserPrototypes.buildUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shopping.config.BeansConfig;
import com.shopping.dto.user.UserDetailsDto;
import com.shopping.entity.User;
import com.shopping.exception.error.ErrorCode;
import com.shopping.exception.UserException;
import com.shopping.repository.UserRepository;
import com.shopping.service.impl.UserServiceImpl;
import java.util.Optional;
import ma.glasnost.orika.MapperFacade;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @Spy
  private MapperFacade mapper = new BeansConfig().mapper();

  @InjectMocks
  private UserServiceImpl userService;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void findById_USER_NOT_FOUND() {
    //GIVEN
    final Long id = 1L;

    //WHEN THEN
    assertThatThrownBy(() -> userService.findById(id))
        .isInstanceOf(UserException.class)
        .hasFieldOrPropertyWithValue("errorCode", ErrorCode.USER_NOT_FOUND);
  }

  @Test
  public void findById_USER_IS_BLOCKED() {
    //GIVEN
    final Long id = 1L;
    final User user = buildUser("admin", "admin@gmail.com", true);

    when(userRepository.findById(id)).thenReturn(Optional.of(user));

    //WHEN THEN
    assertThatThrownBy(() -> userService.findById(id))
        .isInstanceOf(UserException.class)
        .hasFieldOrPropertyWithValue("errorCode", ErrorCode.USER_IS_BLOCKED);
  }

  @Test
  public void findById_Success() {
    //GIVEN
    final Long id = 1L;
    final User user = buildUser("admin", "admin@gmail.com", false);

    when(userRepository.findById(id)).thenReturn(Optional.of(user));

    //WHEN
    final UserDetailsDto response = userService.findById(id);

    //THEN
    assertThat(response).isNotNull();
    assertThat(response.getName()).isEqualTo(user.getName());
    assertThat(response.getEmail()).isEqualTo(user.getEmail());
  }

  @Test
  public void blocked_Success() {
    //GIVEN
    final Long id = 1L;
    final User user = buildUser("admin", "admin@gmail.com", false);

    when(userRepository.findById(id)).thenReturn(Optional.of(user));

    //WHEN
    userService.blocked(id);

    //THEN
    ArgumentCaptor<User> userCapture = ArgumentCaptor.forClass(User.class);
    verify(userRepository).save(userCapture.capture());

    User savedUser = userCapture.getValue();

    assertThat(savedUser).isNotNull();
    assertThat(savedUser.isBlock()).isEqualTo(true);
  }

  @Test
  public void unBlocked_USER_NOT_FOUND() {
    //GIVEN
    final Long id = 1L;

    //WHEN THEN
    assertThatThrownBy(() -> userService.unBlocked(id))
        .isInstanceOf(UserException.class)
        .hasFieldOrPropertyWithValue("errorCode", ErrorCode.USER_NOT_FOUND);
  }

  @Test
  public void unBlocked_Success() {
    //GIVEN
    final Long id = 1L;
    final User user = buildUser("admin", "admin@gmail.com", true);

    when(userRepository.findById(id)).thenReturn(Optional.of(user));

    //WHEN
    userService.unBlocked(id);

    //THEN
    ArgumentCaptor<User> userCapture = ArgumentCaptor.forClass(User.class);
    verify(userRepository).save(userCapture.capture());

    User savedUser = userCapture.getValue();

    assertThat(savedUser).isNotNull();
    assertThat(savedUser.isBlock()).isEqualTo(false);
  }
}
