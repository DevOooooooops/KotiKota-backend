package org.hackaton.kotikota.endpoint.rest.security;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.hackaton.kotikota.endpoint.rest.exception.ForbiddenException;
import org.hackaton.kotikota.endpoint.rest.security.auth.AuthProvider;
import org.hackaton.kotikota.endpoint.rest.security.auth.AuthenticatedResourceProvider;
import org.hackaton.kotikota.endpoint.rest.security.matcher.SelfUserMatcher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@Slf4j
@EnableWebSecurity
public class SecurityConf {
  public static final String AUTHORIZATION_HEADER = "Authorization";
  private final AuthProvider authProvider;
  private final HandlerExceptionResolver exceptionResolver;
  private final AuthenticatedResourceProvider authenticatedResourceProvider;

  public SecurityConf(
      AuthProvider authProvider,
      @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver,
      AuthenticatedResourceProvider authenticatedResourceProvider) {
    this.authProvider = authProvider;
    this.exceptionResolver = exceptionResolver;
    this.authenticatedResourceProvider = authenticatedResourceProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    return new ProviderManager(authProvider);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.exceptionHandling(
            (exceptionHandler) ->
                exceptionHandler
                    .authenticationEntryPoint(
                        // note(spring-exception)
                        // https://stackoverflow.com/questions/59417122/how-to-handle-usernamenotfoundexception-spring-security
                        // issues like when a user tries to access a resource
                        // without appropriate authentication elements
                        (req, res, e) ->
                            exceptionResolver.resolveException(
                                req, res, null, forbiddenWithRemoteInfo(e, req)))
                    .accessDeniedHandler(
                        // note(spring-exception): issues like when a user not having required roles
                        (req, res, e) ->
                            exceptionResolver.resolveException(
                                req, res, null, forbiddenWithRemoteInfo(e, req))))
        .csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
        .addFilterBefore(
            bearerFilter(
                new NegatedRequestMatcher(
                    new OrRequestMatcher(
                        new AntPathRequestMatcher("/**", OPTIONS.toString()),
                        new AntPathRequestMatcher("/hello", GET.toString()),
                        new AntPathRequestMatcher("/projects", GET.toString()),
                        new AntPathRequestMatcher("/users", POST.toString()),
                        new AntPathRequestMatcher("/projects/*/contributors", GET.toString()),
                        new AntPathRequestMatcher("/projects/*/comments", GET.toString()),
                        new AntPathRequestMatcher("/projects/*", GET.toString())))),
            AnonymousAuthenticationFilter.class)
        .authorizeHttpRequests(
            (authorize) ->
                authorize
                    .requestMatchers(OPTIONS, "/**")
                    .permitAll()
                    .requestMatchers(GET, "/hello")
                    .permitAll()
                    .requestMatchers(GET, "/projects")
                    .permitAll()
                    .requestMatchers(GET, "/projects/*")
                    .permitAll()
                    .requestMatchers(GET, "/projects/*/comments")
                    .permitAll()
                    .requestMatchers(GET, "/projects/*/donations")
                    .permitAll()
                    .requestMatchers(GET, "/projects/*/contributors")
                    .permitAll()
                    .requestMatchers(
                        new SelfUserMatcher(PUT, "users/*/projects", authenticatedResourceProvider))
                    .authenticated()
                    .requestMatchers(PUT, "/projects/*/comments")
                    .authenticated()
                    .requestMatchers(POST, "/projects/*/donations")
                    .authenticated()
                    .requestMatchers(GET, "/users")
                    .authenticated()
                    .requestMatchers(POST, "/users")
                    .anonymous()
                    .requestMatchers(
                        new SelfUserMatcher(GET, "/users/*", authenticatedResourceProvider))
                    .authenticated()
                    .requestMatchers(
                        new SelfUserMatcher(PUT, "/users/*/profile", authenticatedResourceProvider))
                    .authenticated()
                    .requestMatchers(GET, "/whoami")
                    .authenticated()
                    .requestMatchers(
                        new SelfUserMatcher(
                            POST, "/users/*/deposits", authenticatedResourceProvider))
                    .authenticated()
                    .requestMatchers("/**")
                    .denyAll());
    return http.build();
  }

  private BearerAuthFilter bearerFilter(RequestMatcher requestMatcher) {
    BearerAuthFilter bearerFilter = new BearerAuthFilter(requestMatcher, AUTHORIZATION_HEADER);
    bearerFilter.setAuthenticationManager(authenticationManager());
    bearerFilter.setAuthenticationSuccessHandler(
        (httpServletRequest, httpServletResponse, authentication) -> {});
    bearerFilter.setAuthenticationFailureHandler(
        (req, res, e) ->
            // note(spring-exception)
            // issues like when a user is not found(i.e. UsernameNotFoundException)
            // or other exceptions thrown inside authentication provider.
            // In fact, this handles other authentication exceptions that are
            // not handled by AccessDeniedException and AuthenticationEntryPoint
            exceptionResolver.resolveException(req, res, null, forbiddenWithRemoteInfo(e, req)));
    return bearerFilter;
  }

  private Exception forbiddenWithRemoteInfo(Exception e, HttpServletRequest req) {
    log.info(
        String.format(
            "Access is denied for remote caller: address=%s, host=%s, port=%s",
            req.getRemoteAddr(), req.getRemoteHost(), req.getRemotePort()));
    return new ForbiddenException(e.getMessage());
  }
}
