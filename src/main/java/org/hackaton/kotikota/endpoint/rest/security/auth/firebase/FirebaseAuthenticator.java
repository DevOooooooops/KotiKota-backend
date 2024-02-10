package org.hackaton.kotikota.endpoint.rest.security.auth.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.AllArgsConstructor;
import org.hackaton.kotikota.Generated;
import org.hackaton.kotikota.endpoint.rest.exception.ForbiddenException;
import org.hackaton.kotikota.endpoint.rest.security.UsernamePasswordAuthenticator;
import org.hackaton.kotikota.endpoint.rest.security.model.Principal;
import org.hackaton.kotikota.repository.model.User;
import org.hackaton.kotikota.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Generated
public class FirebaseAuthenticator implements UsernamePasswordAuthenticator {
  private final FirebaseAuth firebaseAuth;
  private final UserService userService;

  private FirebaseToken validateToken(String token) {
    try {
      return firebaseAuth.verifyIdToken(token);
    } catch (FirebaseAuthException e) {
      throw new ForbiddenException(e.getMessage());
    }
  }

  public String getEmail(String token) {
    return validateToken(token).getEmail();
  }

  @Override
  public UserDetails retrieveUser(
      String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
    String bearer = getBearerFromHeader(usernamePasswordAuthenticationToken);
    String email = getEmail(bearer);
    try {
      User byEmail = userService.getByEmail(email);
      return new Principal(byEmail, bearer);
    } catch (Exception ignored) {
      throw new UsernameNotFoundException("Bad Credentials");
    }
  }

  private String getBearerFromHeader(
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
    Object tokenObject = usernamePasswordAuthenticationToken.getCredentials();
    if (!(tokenObject instanceof String) || !((String) tokenObject).startsWith("Bearer ")) {
      return null;
    }
    return ((String) tokenObject).substring("Bearer ".length()).trim();
  }
}
