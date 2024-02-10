package org.hackaton.kotikota.endpoint.rest.security.auth;

import lombok.AllArgsConstructor;
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
public class BearerAuthenticator implements UsernamePasswordAuthenticator {
    private final UserService userService;

    @Override
    public UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        String email = getEmailFromHeader(usernamePasswordAuthenticationToken);
        try{
            User byEmail = userService.getByEmail(email);
            return new Principal(byEmail, email);
        } catch (Exception ignored){
            throw new UsernameNotFoundException("bad credentials");
        }
    }

    private String getEmailFromHeader(
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        Object tokenObject = usernamePasswordAuthenticationToken.getCredentials();
        if (!(tokenObject instanceof String) || !((String) tokenObject).startsWith("EMAIL ")) {
            return null;
        }
        return ((String) tokenObject).substring("EMAIL ".length()).trim();
    }
}
