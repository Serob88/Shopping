package com.shopping.service.impl.prototipes;

import com.shopping.dto.product.ReviewRequestDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ReviewPrototypes extends Prototype {

  public static ReviewRequestDto buildCommentRequestDto(final String text, final int rate) {
    ReviewRequestDto requestDto = new ReviewRequestDto();
    requestDto.setComment(text);
    requestDto.setRate(rate);

    return requestDto;
  }
}
