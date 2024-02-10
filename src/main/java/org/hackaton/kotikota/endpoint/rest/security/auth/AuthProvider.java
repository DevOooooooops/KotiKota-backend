package org.hackaton.kotikota.endpoint.rest.security.auth;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hackaton.kotikota.endpoint.rest.security.UsernamePasswordAuthenticator;
import org.hackaton.kotikota.endpoint.rest.security.auth.firebase.FirebaseAuthenticator;
import org.hackaton.kotikota.endpoint.rest.security.model.Principal;
import org.hackaton.kotikota.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class AuthProvider extends AbstractUserDetailsAuthenticationProvider {
  private final UsernamePasswordAuthenticator authenticator;
  private static final String BEARER_PREFIX = "Bearer ";

  @Override
  protected void additionalAuthenticationChecks(
      UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
      throws AuthenticationException {
    // nothing
  }

  @Override
  protected UserDetails retrieveUser(
      String username, UsernamePasswordAuthenticationToken authentication)
      throws AuthenticationException {
    return authenticator.retrieveUser(username, authentication);
  }
  public static Principal getPrincipal() {
    SecurityContext context = SecurityContextHolder.getContext();
    Authentication authentication = context.getAuthentication();
    return (Principal) authentication.getPrincipal();
  }
}
