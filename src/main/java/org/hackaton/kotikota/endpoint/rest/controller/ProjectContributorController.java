package org.hackaton.kotikota.endpoint.rest.controller;

import lombok.AllArgsConstructor;
import org.hackaton.kotikota.endpoint.rest.model.ProjectContributors;
import org.hackaton.kotikota.service.ProjectContributorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ProjectContributorController {
  private final ProjectContributorService service;

  @GetMapping("/projects/{projectId}/contributors")
  public ProjectContributors getProjectContributors(@PathVariable String projectId) {
    return service.getProjectContributors(projectId);
  }
}
