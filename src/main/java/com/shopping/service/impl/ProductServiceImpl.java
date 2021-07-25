package com.shopping.service.impl;

import com.shopping.dto.product.ProductRequestDto;
import com.shopping.dto.product.ProductResponseDto;
import com.shopping.entity.Product;
import com.shopping.exception.error.ErrorCode;
import com.shopping.exception.ProductException;
import com.shopping.repository.ProductRepository;
import com.shopping.service.ProductService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final MapperFacade mapper;

  @Override
  @Transactional
  public ProductResponseDto creat(ProductRequestDto request) {
    log.info("Trying to save product: {}", request);

    Product product = mapper.map(request, Product.class);

    productRepository.save(product);

    return mapper.map(product, ProductResponseDto.class);
  }

  @Override
  @Transactional
  public ProductResponseDto findById(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND));

    return mapper.map(product, ProductResponseDto.class);
  }

  @Override
  @Transactional
  public List<ProductResponseDto> findByName(String name, Pageable pageable) {
    Page<Product> products = productRepository.findByName(searchLikePattern(name), pageable);

    return mapper.mapAsList(products, ProductResponseDto.class);
  }

  @Override
  @Transactional
  public List<ProductResponseDto> findByRate(int rate, Pageable pageable) {
    Page<Product> products = productRepository.findByRate(rate, pageable);

    return mapper.mapAsList(products, ProductResponseDto.class);
  }

  @Override
  @Transactional
  public List<ProductResponseDto> findAll(Pageable pageable) {
     Page<Product> products = productRepository.findAll(pageable);
    return mapper.mapAsList(products, ProductResponseDto.class);
  }

  @Override
  @Transactional
  public ProductResponseDto update(Long id, ProductRequestDto request) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND));

    product.setCategory(request.getCategory());
    product.setName(request.getName());

    log.info("trying to saved product: {}", product);
    productRepository.save(product);
    return mapper.map(product, ProductResponseDto.class);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND));

    productRepository.delete(product);
  }

  private String searchLikePattern(final String value) {
    return "%" + value.toLowerCase() + "%";
  }

}
