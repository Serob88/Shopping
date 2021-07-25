package com.shopping.config.proportis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(
    prefix = "authorization.security.filter")
public class UrlConfigProperties {

  private List<String> ignoredUrlPatterns = new ArrayList();
  private Map<String, UrlConfigProperties.RequestUrlDetails> ignoredSpecificUrlPatterns = new HashMap();
  private Map<String, UrlConfigProperties.RequestUrlDetails> expirationIgnoreUrlPatterns = new HashMap();

  public UrlConfigProperties() {
  }

  public List<String> getIgnoredUrlPatterns() {
    return this.ignoredUrlPatterns;
  }

  public Map<String, UrlConfigProperties.RequestUrlDetails> getIgnoredSpecificUrlPatterns() {
    return this.ignoredSpecificUrlPatterns;
  }

  public Map<String, UrlConfigProperties.RequestUrlDetails> getExpirationIgnoreUrlPatterns() {
    return this.expirationIgnoreUrlPatterns;
  }

  public void setIgnoredUrlPatterns(final List<String> ignoredUrlPatterns) {
    this.ignoredUrlPatterns = ignoredUrlPatterns;
  }

  public void setIgnoredSpecificUrlPatterns(
      final Map<String, UrlConfigProperties.RequestUrlDetails> ignoredSpecificUrlPatterns) {
    this.ignoredSpecificUrlPatterns = ignoredSpecificUrlPatterns;
  }

  public void setExpirationIgnoreUrlPatterns(
      final Map<String, UrlConfigProperties.RequestUrlDetails> expirationIgnoreUrlPatterns) {
    this.expirationIgnoreUrlPatterns = expirationIgnoreUrlPatterns;
  }


  public static class RequestUrlDetails {

    private String pattern;
    private String method;

    public RequestUrlDetails() {
    }

    public String getPattern() {
      return this.pattern;
    }

    public String getMethod() {
      return this.method;
    }

    public void setPattern(final String pattern) {
      this.pattern = pattern;
    }

    public void setMethod(final String method) {
      this.method = method;
    }
  }
}
