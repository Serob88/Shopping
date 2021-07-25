package com.shopping.controller;

import com.shopping.api.ReviewApi;
import com.shopping.dto.CommentRequestDto;
import com.shopping.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController implements ReviewApi {

  private final ReviewService reviewService;

  @Override
  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
  @PostMapping(value = "/{productId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> comment(@PathVariable(value = "productId") Long productId,
        @RequestBody CommentRequestDto request) {

    log.info("Trying to commented product");
    reviewService.comment(productId, request);

    return ResponseEntity.ok().build();
  }

  @Override
  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
  @PostMapping(value = "/{productId}/rates", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> rate(@PathVariable(value = "productId") Long productId,
      @RequestParam(value = "rate") int rate) {

    log.info("Trying to rated product");
    reviewService.rate(productId, rate);

    return ResponseEntity.ok().build();
  }
}
