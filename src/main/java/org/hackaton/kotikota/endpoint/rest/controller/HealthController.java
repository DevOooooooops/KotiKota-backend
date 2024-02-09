package org.hackaton.kotikota.endpoint.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
  @GetMapping("/hello")
  public String checkHealth() {
    return "Hello";
  }
}
