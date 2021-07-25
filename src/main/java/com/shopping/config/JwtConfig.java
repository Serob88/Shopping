package com.shopping.config;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public JwtBuilder jwtBuilder() {
        return Jwts.builder();
    }

    @Bean
    public JwtParser jwtParser() {
        return Jwts.parser();
    }
}
