package com.shopping.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javax.validation.Validation;
import javax.validation.Validator;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;

@Configuration
public class BeansConfig {

  @Bean
  public Validator validator() {
    return Validation.buildDefaultValidatorFactory().getValidator();
  }

  @Bean
  public AntPathMatcher antPathMatcher() {
    return new AntPathMatcher();
  }

  @Bean
  public MapperFacade mapper() {
    return new DefaultMapperFactory.Builder().build().getMapperFacade();
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper().registerModule(new JavaTimeModule());
  }

}
