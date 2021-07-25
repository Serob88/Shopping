package com.shopping.security.manager;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.exception.SecurityException;
import com.shopping.exception.error.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class JwtManager {

  private final static String INVALID_CLAIMS_MESSAGE = "Invalid claims";

  private final ObjectMapper objectMapper;
  private final JwtBuilder jwtBuilder;
  private final JwtParser jwtParser;
  private final Validator validator;

  public <T> String generateTokenFromMappedClaims(final T mappedClaims, final String signatureKey,
      final SignatureAlgorithm algorithm) {
    final Map<String, Object> claims = getClaimsFromValidatedMappedClaims(mappedClaims);
    return generateToken(claims, algorithm, signatureKey);
  }

  public <T> T getValidatedMappedClaimsFromToken(final String token, final String signatureKey,
      final Class<T> toValueType) {
    try {
      Claims claims = getClaimsFromToken(token, signatureKey);
      return getValidatedMappedClaims(claims, toValueType);
    } catch (ExpiredJwtException ex) {
      log.debug("The token has expired", ex);
      throw new SecurityException(ErrorCode.ACCESS_TOKEN_EXPIRED);
    } catch (JwtException | IllegalStateException ex) {
      log.debug("The token is invalid", ex);
      throw new SecurityException(ErrorCode.ACCESS_TOKEN_INVALID);
    }
  }

  private Claims getClaimsFromToken(final String token, final String signatureKey) {
    return jwtParser
        .setSigningKey(signatureKey.getBytes(StandardCharsets.UTF_8))
        .parseClaimsJws(token)
        .getBody();
  }

  private String generateToken(final Map<String, Object> claims, final SignatureAlgorithm algorithm,
      final String signatureKey) {
    return jwtBuilder
        .setClaims(claims)
        .signWith(algorithm, signatureKey.getBytes(StandardCharsets.UTF_8))
        .compact();
  }

  private <T> Map<String, Object> getClaimsFromValidatedMappedClaims(final T mappedClaims) {
    validateMappedClaims(mappedClaims);
    return objectMapper.convertValue(mappedClaims, new TypeReference<Map<String, Object>>() {
    });
  }

  private <T> T getValidatedMappedClaims(final Claims claims, final Class<T> toValueType) {
    T mappedClaims = objectMapper.convertValue(claims, toValueType);
    validateMappedClaims(mappedClaims);
    return mappedClaims;
  }

  private <T> void validateMappedClaims(final T claims) {
    Set<ConstraintViolation<T>> violations = validator.validate(claims);
    if (!violations.isEmpty()) {
      violations.forEach(violation -> log.error("{} {}", violation.getPropertyPath(), violation.getMessage()));
      throw new IllegalStateException(INVALID_CLAIMS_MESSAGE);
    }
  }

}
