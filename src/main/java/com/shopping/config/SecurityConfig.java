package com.shopping.config;


import com.shopping.security.filter.RestAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final RestAuthorizationFilter authorizationFilter;

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {

    // @formatter:off

    httpSecurity
        .cors()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .anyRequest()
        .permitAll()
        .and()
        .csrf()
        .disable()
        .formLogin()
        .disable()
        .httpBasic()
        .disable()
        .logout()
        .disable();

    httpSecurity.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

    // @formatter:on

  }

}
