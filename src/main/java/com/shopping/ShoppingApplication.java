package com.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.shopping")
public class ShoppingApplication {

  public static void main(String[] args) {
    SpringApplication.run(ShoppingApplication.class, args);
  }

}
