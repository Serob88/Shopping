package com.shopping.security.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.Instant;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AccessTokenClaims {

  @NotBlank
  protected String sub;

  @NotBlank
  protected String iss;

  @NotBlank
  protected String username;

  @NotNull
  protected Long userId;

  @NotNull
  protected Instant exp;

  @NotNull
  protected Set<String> authorities;

}
