package com.shopping.service;

import com.shopping.dto.product.ProductRequestDto;
import com.shopping.dto.product.ProductResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ProductService {

  /**
   * Creat product.
   *
   * @param request {@link ProductRequestDto}
   * @return {@link ProductResponseDto}
   */
  ProductResponseDto creat(ProductRequestDto request);

  /**
   * Find product by id.
   *
   * @param id Long product id
   * @return {@link ProductResponseDto}
   */
  ProductResponseDto findById(Long id);

  /**
   * Find products by name.
   *
   * @param name product name
   * @return list of ProductResponseDto {@link ProductResponseDto}
   */
  List<ProductResponseDto> findByName(String name, Pageable pageable);

  /**
   * Find products by rate.
   *
   * @param rate product rate
   * @return list of ProductResponseDto {@link ProductResponseDto}
   */
  List<ProductResponseDto> findByRate(int rate, Pageable pageable);

  /**
   * Find all products.
   *
   * @return list of ProductResponseDto {@link ProductResponseDto}
   */
  List<ProductResponseDto> findAll(Pageable pageable);

  /**
   * Update product.
   *
   * @param id      Long product id
   * @param request {@link ProductRequestDto}
   * @return {@link ProductResponseDto}
   */
  ProductResponseDto update(Long id, ProductRequestDto request);

  /**
   * Remove product by id.
   *
   * @param id Long product id
   */
  void delete(Long id);

}
