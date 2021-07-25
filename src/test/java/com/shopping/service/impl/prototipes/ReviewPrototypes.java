package com.shopping.service.impl.prototipes;

import com.shopping.dto.CommentRequestDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ReviewPrototypes extends Prototype {

  public static CommentRequestDto buildCommentRequestDto(final String text) {
    CommentRequestDto requestDto = new CommentRequestDto();
    requestDto.setText(text);

    return requestDto;
  }
}
