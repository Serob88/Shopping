package com.shopping.config.proportis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfigProperties {

  private String title;
  private String description;
  private Contact contact;

  @Data
  public static class Contact {
    private String name;
    private String url;
    private String email;
  }

}
