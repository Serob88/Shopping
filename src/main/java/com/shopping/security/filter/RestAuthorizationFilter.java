package com.shopping.security.filter;

import com.shopping.exception.SecurityException;
import com.shopping.exception.error.ErrorCode;
import com.shopping.security.manager.JwtManager;
import com.shopping.security.pojo.AccessTokenClaims;
import com.shopping.security.pojo.ContextUser;
import com.shopping.config.proportis.JwtConfigProperties;
import com.shopping.config.proportis.UrlConfigProperties;
import com.shopping.security.SecurityUtil;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
public class RestAuthorizationFilter extends OncePerRequestFilter {

  private final JwtManager jwtManager;
  private final SecurityUtil securityUtil;
  private final MapperFacade mapper;
  private final JwtConfigProperties jwtConfigProperties;
  private final UrlConfigProperties urlConfigProperties;
  private final AntPathMatcher pathMatcher;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String accessToken = this.retrieveAccessToken(request);
    if (StringUtils.isNotBlank(accessToken)) {
      log.info("Successfully parsed JWT token from headers. Trying to authenticate user.");
      this.setAuthentication(accessToken, request);

      try {
        filterChain.doFilter(request, response);
      } finally {
        this.securityUtil.clearAuthentication();
      }
    } else {
      log.info("No JWT token found in headers for uri {} {}", request.getMethod(), request.getRequestURI());
      throw new SecurityException(ErrorCode.ACCESS_TOKEN_REQUIRED);
    }
  }

  private void setAuthentication(final String authToken, final HttpServletRequest request) {
    AccessTokenClaims accessTokenClaims = this.jwtManager.getValidatedMappedClaimsFromToken(authToken,
        jwtConfigProperties.getSignatureKey(), AccessTokenClaims.class);

    List<GrantedAuthority> authorities = accessTokenClaims.getAuthorities().stream()
        .map(SimpleGrantedAuthority::new).collect(
            Collectors.toList());

    ContextUser contextUser = this.mapper.map(accessTokenClaims, ContextUser.class);
    contextUser.setAuthToken(authToken);

    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(contextUser,
        null, authorities);
    this.securityUtil.setAuthentication(authenticationToken);
    log.info("User successfully authenticated with userId: {} and roles: {}", contextUser.getUserId(),
        contextUser.getAuthorities());
  }

  private String retrieveAccessToken(final HttpServletRequest request) {
    String authBearerToken = Optional.ofNullable(request.getHeader(jwtConfigProperties.getHeader()))
        .orElse(null);
    return StringUtils
        .trimToNull(StringUtils.substringAfter(authBearerToken, jwtConfigProperties.getAuthorizationPrefix()));
  }

  protected boolean shouldNotFilter(HttpServletRequest request) {
    boolean isIgnoredUrl = this.urlConfigProperties.getIgnoredUrlPatterns().stream()
        .anyMatch((ignorableUri) -> this.pathMatcher.match(ignorableUri, request.getRequestURI()));

    boolean isIgnoredSpecificUrl = this.urlConfigProperties.getIgnoredSpecificUrlPatterns().values().stream()
        .anyMatch((value) -> this.pathMatcher.match(value.getPattern(), request.getRequestURI()) && request.getMethod()
            .equalsIgnoreCase(value.getMethod()));

    return isIgnoredUrl || isIgnoredSpecificUrl;
  }

  public RestAuthorizationFilter(final JwtManager jwtManager,
      final UrlConfigProperties urlConfigProperties, final JwtConfigProperties jwtConfigProperties,
      final SecurityUtil securityUtil, final AntPathMatcher pathMatcher, final MapperFacade mapper) {
    this.jwtManager = jwtManager;
    this.jwtConfigProperties = jwtConfigProperties;
    this.urlConfigProperties = urlConfigProperties;
    this.securityUtil = securityUtil;
    this.pathMatcher = pathMatcher;
    this.mapper = mapper;
  }
}

