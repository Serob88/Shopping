package com.shopping.service;

import com.shopping.dto.product.ReviewRequestDto;
import com.shopping.dto.product.ReviewResponseDto;

public interface ReviewService {

  /**
   * Review product.
   *
   * @param productId Long product id
   * @param request {@link ReviewRequestDto}
   * @return {@link ReviewResponseDto}
   */
  ReviewResponseDto review(Long productId, ReviewRequestDto request);

  /**
   * Find product rate avg.
   *
   * @param productId Long product id
   * @return Double product rate avg
   */
  Double findProductRate(final Long productId);
}