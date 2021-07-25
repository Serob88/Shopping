package com.shopping.service.impl.service;

import static com.shopping.service.impl.prototipes.ProductPrototypes.buildProduct;
import static com.shopping.service.impl.prototipes.ProductPrototypes.buildProductRequestDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shopping.config.BeansConfig;
import com.shopping.dto.product.ProductRequestDto;
import com.shopping.dto.product.ProductResponseDto;
import com.shopping.entity.Product;
import com.shopping.exception.error.ErrorCode;
import com.shopping.exception.ProductException;
import com.shopping.repository.ProductRepository;
import com.shopping.service.impl.ProductServiceImpl;
import java.util.Optional;
import ma.glasnost.orika.MapperFacade;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class ProductServiceImplTest {

  @Mock
  private ProductRepository productRepository;

  @Spy
  private MapperFacade mapper = new BeansConfig().mapper();

  @InjectMocks
  private ProductServiceImpl productService;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void creat_Success() {
    //GIVEN
    final ProductRequestDto request = buildProductRequestDto("Product", "clothes");

    //WHEN
    productService.creat(request);

    //THEN
    ArgumentCaptor<Product> productCapture = ArgumentCaptor.forClass(Product.class);
    verify(productRepository).save(productCapture.capture());

    Product savedProduct = productCapture.getValue();

    assertThat(savedProduct).isNotNull();
    assertThat(savedProduct.getCategory()).isEqualTo(request.getCategory());
    assertThat(savedProduct.getName()).isEqualTo(request.getName());
  }

  @Test
  public void findById_PRODUCT_NOT_FOUND() {
    //GIVEN
    final Long id = 1L;

    //WHEN THEN
    assertThatThrownBy(() -> productService.findById(id))
        .isInstanceOf(ProductException.class)
        .hasFieldOrPropertyWithValue("errorCode", ErrorCode.PRODUCT_NOT_FOUND);
  }

  @Test
  public void findById_Success() {
    //GIVEN
    final Long id = 1L;
    final Product product = buildProduct("Product", "clothes");

    when(productRepository.findById(id)).thenReturn(Optional.of(product));

    //WHEN
    final ProductResponseDto response = productService.findById(id);

    //THEN
    assertThat(response).isNotNull();
    assertThat(response.getCategory()).isEqualTo(product.getCategory());
    assertThat(response.getName()).isEqualTo(product.getName());
  }

  @Test
  public void update_PRODUCT_NOT_FOUND() {
    //GIVEN
    final Long id = 1L;
    final ProductRequestDto request = buildProductRequestDto("newProduct", "shoes");

    //WHEN THEN
    assertThatThrownBy(() -> productService.update(id, request))
        .isInstanceOf(ProductException.class)
        .hasFieldOrPropertyWithValue("errorCode", ErrorCode.PRODUCT_NOT_FOUND);
  }

  @Test
  public void update_Success() {
    //GIVEN
    final Long id = 1L;
    final ProductRequestDto request = buildProductRequestDto("newProduct", "shoes");
    final Product product = buildProduct("Product", "clothes");

    when(productRepository.findById(id)).thenReturn(Optional.of(product));

    //WHEN
    productService.update(id, request);

    //THEN
    ArgumentCaptor<Product> productCapture = ArgumentCaptor.forClass(Product.class);
    verify(productRepository).save(productCapture.capture());

    Product savedProduct = productCapture.getValue();

    assertThat(savedProduct).isNotNull();
    assertThat(savedProduct.getName()).isEqualTo(request.getName());
    assertThat(savedProduct.getCategory()).isEqualTo(request.getCategory());
  }
}
