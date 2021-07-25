package com.shopping.security;

import com.shopping.security.pojo.ContextUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityUtil {

  public Long getCurrentUserId() {
    return ((ContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
  }

  public ContextUser getCurrentUser() {
    return (ContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  public void setAuthentication(final UsernamePasswordAuthenticationToken auth) {
    SecurityContextHolder.getContext().setAuthentication(auth);
  }

  public void clearAuthentication() {
    SecurityContextHolder.clearContext();
  }

  public SecurityUtil() {
  }
}
