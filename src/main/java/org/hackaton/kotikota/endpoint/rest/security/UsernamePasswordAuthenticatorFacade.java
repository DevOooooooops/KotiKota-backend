package org.hackaton.kotikota.endpoint.rest.security;

import lombok.AllArgsConstructor;
import org.hackaton.kotikota.Generated;
import org.hackaton.kotikota.endpoint.rest.security.auth.BearerAuthenticator;
import org.hackaton.kotikota.endpoint.rest.security.auth.firebase.FirebaseAuthenticator;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Primary
@Component
@AllArgsConstructor
@Generated
public class UsernamePasswordAuthenticatorFacade implements UsernamePasswordAuthenticator {
  private final BearerAuthenticator bearerAuthenticator;
  private final FirebaseAuthenticator firebaseAuthenticator;

  @Override
  public UserDetails retrieveUser(
      String username, UsernamePasswordAuthenticationToken authentication) {
    try {
      return bearerAuthenticator.retrieveUser(username, authentication);
    } catch (AuthenticationException ignored) {
      return firebaseAuthenticator.retrieveUser(username, authentication);
    }
  }
}
