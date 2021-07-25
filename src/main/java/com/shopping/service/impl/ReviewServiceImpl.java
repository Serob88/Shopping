package com.shopping.service.impl;

import com.shopping.dto.CommentRequestDto;
import com.shopping.entity.Product;
import com.shopping.entity.Review;
import com.shopping.entity.User;
import com.shopping.exception.error.ErrorCode;
import com.shopping.exception.ProductException;
import com.shopping.exception.UserException;
import com.shopping.repository.ProductRepository;
import com.shopping.repository.ReviewRepository;
import com.shopping.security.AuthorizationFacade;
import com.shopping.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

  private final ReviewRepository reviewRepository;
  private final ProductRepository productRepository;

  private final AuthorizationFacade authorizationFacade;

  @Override
  @Transactional
  public void comment(Long productId, CommentRequestDto request) {
    User user = currentUser();

    Product product = findProduct(productId);

    Review review = new Review();
    review.setUserId(user.getId());
    review.setComment(request.getText());
    review.setProductId(product.getId());

    log.info("trying to saved review: {}", review);
    reviewRepository.save(review);
  }

  @Override
  @Transactional
  public void rate(Long productId, int rate) {
    User user = currentUser();

    Product product = findProduct(productId);

    Review review = new Review();
    review.setUserId(user.getId());
    review.setRate(rate);
    review.setProductId(product.getId());

    log.info("trying to saved review: {}", review);
    reviewRepository.save(review);
  }

  private User currentUser() {
    User user = authorizationFacade.getUser();

    if (user.isBlock()) {
      throw new UserException(ErrorCode.USER_IS_BLOCKED);
    }

    return user;
  }

  private Product findProduct(final Long productId) {
    return productRepository.findById(productId)
        .orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND));
  }
}
