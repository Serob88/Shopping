package com.shopping.service.impl.service;

import static com.shopping.service.impl.prototipes.AuthPrototypes.buildSignInRequestDto;
import static com.shopping.service.impl.prototipes.AuthPrototypes.buildSignUpRequestDto;
import static com.shopping.service.impl.prototipes.UserPrototypes.buildUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.shopping.config.BeansConfig;
import com.shopping.dto.signin.SignInRequestDto;
import com.shopping.dto.signup.SignUpRequestDto;
import com.shopping.dto.signup.SignUpResponseDto;
import com.shopping.entity.User;
import com.shopping.exception.error.ErrorCode;
import com.shopping.exception.UserException;
import com.shopping.repository.RoleRepository;
import com.shopping.repository.UserRepository;
import com.shopping.service.impl.AuthServiceImpl;
import java.sql.Date;
import java.util.Optional;
import ma.glasnost.orika.MapperFacade;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class AuthServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private RoleRepository roleRepository;

  @Spy
  private MapperFacade mapper = new BeansConfig().mapper();

  @InjectMocks
  private AuthServiceImpl authService;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void signUp_EMAIL_ALREADY_EXIST() {
    // GIVEN
    final SignUpRequestDto signUpRequestDto = buildSignUpRequestDto("admin@gmail.com",
        "admin", "admin123", Date.valueOf("1997-03-10"));
    final User user = buildUser("admin", "admin@gmail.com");

    when(userRepository.findByEmail(signUpRequestDto.getEmail())).thenReturn(Optional.of(user));

    //WHEN THEN
    assertThatThrownBy(() -> authService.signUp(signUpRequestDto))
        .isInstanceOf(UserException.class)
        .hasFieldOrPropertyWithValue("errorCode", ErrorCode.EMAIL_ALREADY_EXIST);
  }

  @Test
  public void signUp_Success() {
    //GIVEN
    final SignUpRequestDto request = buildSignUpRequestDto("admin@gmail.com",
        "admin", "admin123", Date.valueOf("1997-03-10"));

    when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

    //WHEN
    final SignUpResponseDto response = authService.signUp(request);

    //THEN
    assertThat(response).isNotNull();
    assertThat(response.getEmail()).isEqualTo(request.getEmail());
    assertThat(response.getName()).isEqualTo(request.getName());
  }

  @Test
  public void signIn_SIGN_IN_BAD_CREDENTIALS() {
    //GIVEN
    final SignInRequestDto request = buildSignInRequestDto("admin", "admin@gmail.com");

    when(userRepository.findByEmail(request.getUsername())).thenReturn(Optional.empty());

    //WHEN THEN
    assertThatThrownBy(() -> authService.signIn(request))
        .isInstanceOf(UserException.class)
        .hasFieldOrPropertyWithValue("errorCode", ErrorCode.SIGN_IN_BAD_CREDENTIALS);
  }

  @Test
  public void signIn_USER_IS_BLOCKED() {
    //GIVEN
    final SignInRequestDto request = buildSignInRequestDto("admin", "admin@gmail.com");
    final User user = buildUser("admin", "admin@gmail.com", true);

    when(userRepository.findByEmail(request.getUsername())).thenReturn(Optional.of(user));

    //WHEN THEN
    assertThatThrownBy(() -> authService.signIn(request))
        .isInstanceOf(UserException.class)
        .hasFieldOrPropertyWithValue("errorCode", ErrorCode.USER_IS_BLOCKED);
  }

  @Test
  public void signIn_SIGN_IN_BAD_CREDENTIALS_wrong_password() {
    //GIVEN
    final SignInRequestDto request = buildSignInRequestDto("admin", "admin@gmail.com");
    final User user = buildUser("admin", "admin@gmail.com", "admin123", false);

    when(userRepository.findByEmail(request.getUsername())).thenReturn(Optional.of(user));

    //WHEN THEN
    assertThatThrownBy(() -> authService.signIn(request))
        .isInstanceOf(UserException.class)
        .hasFieldOrPropertyWithValue("errorCode", ErrorCode.SIGN_IN_BAD_CREDENTIALS);
  }
}