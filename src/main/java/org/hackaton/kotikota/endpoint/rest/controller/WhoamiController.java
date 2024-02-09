package org.hackaton.kotikota.endpoint.rest.controller;

import lombok.AllArgsConstructor;
import org.hackaton.kotikota.endpoint.rest.mapper.UserMapper;
import org.hackaton.kotikota.endpoint.rest.model.Whoami;
import org.hackaton.kotikota.endpoint.rest.security.model.Principal;
import org.hackaton.kotikota.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WhoamiController {
  private final UserMapper userMapper;
  private final UserService userService;

  @GetMapping("/whoami")
  public Whoami whoami(@AuthenticationPrincipal Principal principal) {
    return new Whoami().user(userMapper.toRest(userService.getById(principal.getUser().getId())));
  }
}
