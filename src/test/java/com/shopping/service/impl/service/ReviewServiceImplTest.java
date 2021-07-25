package com.shopping.service.impl.service;

import static com.shopping.service.impl.prototipes.ProductPrototypes.buildProduct;
import static com.shopping.service.impl.prototipes.Prototype.buildString;
import static com.shopping.service.impl.prototipes.ReviewPrototypes.buildCommentRequestDto;
import static com.shopping.service.impl.prototipes.UserPrototypes.buildUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shopping.dto.product.CommentRequestDto;
import com.shopping.entity.Product;
import com.shopping.entity.Review;
import com.shopping.entity.User;
import com.shopping.exception.error.ErrorCode;
import com.shopping.exception.ProductException;
import com.shopping.exception.UserException;
import com.shopping.repository.ProductRepository;
import com.shopping.repository.ReviewRepository;
import com.shopping.security.AuthorizationFacade;
import com.shopping.service.impl.ReviewServiceImpl;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ReviewServiceImplTest {

  @Mock
  private ReviewRepository reviewRepository;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private AuthorizationFacade authorizationFacade;

  @InjectMocks
  private ReviewServiceImpl reviewService;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void comment_USER_IS_BLOCKED() {
    //GIVEN
    final Long productId = 1L;
    final CommentRequestDto requestDto = buildCommentRequestDto(buildString());
    final User user = buildUser("admin", "admin@gmail.com", true);

    when(authorizationFacade.getUser()).thenReturn(user);

    //WHEN THEN
    assertThatThrownBy(() -> reviewService.comment(productId, requestDto))
        .isInstanceOf(UserException.class)
        .hasFieldOrPropertyWithValue("errorCode", ErrorCode.USER_IS_BLOCKED);
  }

  @Test
  public void comment_PRODUCT_NOT_FOUND() {
    //GIVEN
    final Long productId = 1L;
    final CommentRequestDto requestDto = buildCommentRequestDto(buildString());
    final User user = buildUser("admin", "admin@gmail.com", false);

    when(authorizationFacade.getUser()).thenReturn(user);

    //WHEN THEN
    assertThatThrownBy(() -> reviewService.comment(productId, requestDto))
        .isInstanceOf(ProductException.class)
        .hasFieldOrPropertyWithValue("errorCode", ErrorCode.PRODUCT_NOT_FOUND);
  }

  @Test
  public void comment_Success() {
    //GIVEN
    final Long productId = 1L;
    final CommentRequestDto requestDto = buildCommentRequestDto(buildString());
    final Product product = buildProduct("Product", "clothes");
    final User user = buildUser("admin", "admin@gmail.com", false);

    when(authorizationFacade.getUser()).thenReturn(user);
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    //WHEN
    reviewService.comment(productId, requestDto);

    //THEN
    ArgumentCaptor<Review> reviewCapture = ArgumentCaptor.forClass(Review.class);
    verify(reviewRepository).save(reviewCapture.capture());

    Review savedReview = reviewCapture.getValue();

    assertThat(savedReview).isNotNull();
    assertThat(savedReview.getComment()).isEqualTo(requestDto.getText());
  }

  @Test
  public void rate_Success() {
    //GIVEN
    final Long productId = 1L;
    final int rate = 5;
    final Product product = buildProduct("Product", "clothes");
    final User user = buildUser("admin", "admin@gmail.com", false);

    when(authorizationFacade.getUser()).thenReturn(user);
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    //WHEN
    reviewService.rate(productId, rate);

    //THEN
    ArgumentCaptor<Review> reviewCapture = ArgumentCaptor.forClass(Review.class);
    verify(reviewRepository).save(reviewCapture.capture());

    Review savedReview = reviewCapture.getValue();

    assertThat(savedReview).isNotNull();
    assertThat(savedReview.getRate()).isEqualTo(rate);
  }
}
