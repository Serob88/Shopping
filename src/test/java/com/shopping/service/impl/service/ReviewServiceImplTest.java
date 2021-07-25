package com.shopping.service.impl.service;

import static com.shopping.service.impl.prototipes.ProductPrototypes.buildProduct;
import static com.shopping.service.impl.prototipes.Prototype.buildString;
import static com.shopping.service.impl.prototipes.ReviewPrototypes.buildCommentRequestDto;
import static com.shopping.service.impl.prototipes.UserPrototypes.buildUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shopping.config.BeansConfig;
import com.shopping.dto.product.ReviewRequestDto;
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
import ma.glasnost.orika.MapperFacade;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class ReviewServiceImplTest {

  @Mock
  private ReviewRepository reviewRepository;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private AuthorizationFacade authorizationFacade;

  @Spy
  private MapperFacade mapper = new BeansConfig().mapper();

  @InjectMocks
  private ReviewServiceImpl reviewService;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void review_USER_IS_BLOCKED() {
    //GIVEN
    final Long productId = 1L;
    final ReviewRequestDto requestDto = buildCommentRequestDto(buildString(),5);
    final User user = buildUser("admin", "admin@gmail.com", true);

    when(authorizationFacade.getUser()).thenReturn(user);

    //WHEN THEN
    assertThatThrownBy(() -> reviewService.review(productId, requestDto))
        .isInstanceOf(UserException.class)
        .hasFieldOrPropertyWithValue("errorCode", ErrorCode.USER_IS_BLOCKED);
  }

  @Test
  public void review_PRODUCT_NOT_FOUND() {
    //GIVEN
    final Long productId = 1L;
    final ReviewRequestDto requestDto = buildCommentRequestDto(buildString(),5);
    final User user = buildUser("admin", "admin@gmail.com", false);

    when(authorizationFacade.getUser()).thenReturn(user);

    //WHEN THEN
    assertThatThrownBy(() -> reviewService.review(productId, requestDto))
        .isInstanceOf(ProductException.class)
        .hasFieldOrPropertyWithValue("errorCode", ErrorCode.PRODUCT_NOT_FOUND);
  }

  @Test
  public void review_Success() {
    //GIVEN
    final Long productId = 1L;
    final ReviewRequestDto requestDto = buildCommentRequestDto(buildString(), 5);
    final Product product = buildProduct("Product", "clothes");
    final User user = buildUser("admin", "admin@gmail.com", false);

    when(authorizationFacade.getUser()).thenReturn(user);
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    //WHEN
    reviewService.review(productId, requestDto);

    //THEN
    ArgumentCaptor<Review> reviewCapture = ArgumentCaptor.forClass(Review.class);
    verify(reviewRepository).save(reviewCapture.capture());

    Review savedReview = reviewCapture.getValue();

    assertThat(savedReview).isNotNull();
    assertThat(savedReview.getComment()).isEqualTo(requestDto.getComment());
    assertThat(savedReview.getRate()).isEqualTo(requestDto.getRate());
  }
}
