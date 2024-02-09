package org.hackaton.kotikota.endpoint.rest.security.auth;

import lombok.AllArgsConstructor;
import org.hackaton.kotikota.repository.model.User;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthenticatedResourceProvider {
  public User getAuthenticatedUser() {
    return AuthProvider.getPrincipal().getUser();
  }
}
