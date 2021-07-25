package com.shopping.service.impl.prototipes;

import com.shopping.dto.product.ProductRequestDto;
import com.shopping.entity.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductPrototypes extends Prototype {

  public static ProductRequestDto buildProductRequestDto(final String name, final String category) {
    ProductRequestDto request = new ProductRequestDto();
    request.setName(name);
    request.setCategory(category);

    return request;
  }

  public static Product buildProduct(final String name, final String category) {
    Product product = new Product();
    product.setName(name);
    product.setCategory(category);

    return product;
  }
}
