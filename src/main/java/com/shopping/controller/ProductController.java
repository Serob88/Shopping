package com.shopping.controller;

import com.shopping.api.ProductApi;
import com.shopping.dto.product.ProductRequestDto;
import com.shopping.dto.product.ProductResponseDto;
import com.shopping.service.ProductService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController implements ProductApi {

  private final ProductService productService;

  @Override
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ProductResponseDto> creat(@RequestBody @Valid final ProductRequestDto request) {

    log.info("Trying to creat product with credentials: {}", request);

    return ResponseEntity.ok(productService.creat(request));
  }

  @Override
  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ProductResponseDto> findById(@PathVariable(value = "id") Long id) {

    log.info("Trying to find product by id: {}", id);

    return ResponseEntity.ok(productService.findById(id));
  }

  @Override
  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
  @GetMapping(value = "/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ProductResponseDto>> findByName(@PathVariable(value = "name") String name, Pageable pageable) {

    log.info("Trying to find products by name: {}", name);

    return ResponseEntity.ok(productService.findByName(name, pageable));
  }

  @Override
  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
  @GetMapping(value = "/rate/{rate}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ProductResponseDto>> findByRate(@PathVariable(value = "rate") int rate, Pageable pageable) {

    log.info("Trying to find products by rate: {}", rate);

    return ResponseEntity.ok(productService.findByRate(rate, pageable));
  }

  @Override
  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<ProductResponseDto>> findAll(Pageable pageable) {

    log.info("Trying to find all products");

    return ResponseEntity.ok(productService.findAll(pageable));
  }

  @Override
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ProductResponseDto> update(@PathVariable(value = "id") Long id,
      @RequestBody @Valid ProductRequestDto request) {

    log.info("Trying to update products by id: {} request: {}", id, request);

    return ResponseEntity.ok(productService.update(id, request));
  }

  @Override
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> delete(@PathVariable(value = "id") final Long id) {

    log.info("Trying to remove product by id: {}", id);
    productService.delete(id);

    return ResponseEntity.ok().build();
  }
}
