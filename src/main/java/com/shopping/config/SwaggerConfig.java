package com.shopping.config;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import com.google.common.collect.Lists;
import com.shopping.config.proportis.JwtConfigProperties;
import com.shopping.config.proportis.SwaggerConfigProperties;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@AllArgsConstructor
@ConditionalOnProperty(prefix = "swagger", value = "enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerConfig implements WebMvcConfigurer {

  private static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

  private final JwtConfigProperties jwtConfigProperties;
  private final SwaggerConfigProperties swaggerConfigProperties;

  @Bean
  public Docket api() {
    Docket docket = new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .useDefaultResponseMessages(false)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.shopping"))
        .paths(or(
            regex("/error")))
        .build()
        .ignoredParameterTypes(Errors.class, Sort.class, Pageable.class, Page.class, ApiIgnore.class)
        .directModelSubstitute(LocalDateTime.class, String.class)
        .useDefaultResponseMessages(false)
        .directModelSubstitute(Instant.class, String.class)
        .securityContexts(Lists.newArrayList(securityContext()))
        .securitySchemes(Collections.singletonList(apiKey()))
        .forCodeGeneration(true);

    docket = docket.select()
        .paths(regex(DEFAULT_INCLUDE_PATTERN))
        .build();

    return docket;
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title(swaggerConfigProperties.getTitle())
        .description(swaggerConfigProperties.getDescription())
        .contact(new Contact(
            swaggerConfigProperties.getContact().getName(),
            swaggerConfigProperties.getContact().getUrl(),
            swaggerConfigProperties.getContact().getEmail()))
        .build();
  }

  private ApiKey apiKey() {
    return new ApiKey("JWT", jwtConfigProperties.getHeader(), "header");
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder()
        .securityReferences(defaultAuth())
        .forPaths(or(
            regex(DEFAULT_INCLUDE_PATTERN))
        )
        .build();
  }

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Lists.newArrayList(
        new SecurityReference("JWT", authorizationScopes));
  }
}
