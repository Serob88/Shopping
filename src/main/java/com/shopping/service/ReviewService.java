package com.shopping.service;

import com.shopping.dto.product.CommentRequestDto;

public interface ReviewService {

  /**
   * Commented product.
   *
   * @param productId Long product id
   * @param request {@link CommentRequestDto}
   */
  void comment(Long productId, CommentRequestDto request);

  /**
   * Rate product.
   *
   * @param productId Long product id
   * @param rate int rate
   */
  void rate(Long productId, int rate);

}
