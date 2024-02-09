package org.hackaton.kotikota.endpoint.rest.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.hackaton.kotikota.endpoint.rest.mapper.ProjectMapper;
import org.hackaton.kotikota.endpoint.rest.model.Project;
import org.hackaton.kotikota.endpoint.rest.validator.ProjectValidator;
import org.hackaton.kotikota.model.BoundedPageSize;
import org.hackaton.kotikota.model.PageFromOne;
import org.hackaton.kotikota.service.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ProjectController {
  private final ProjectMapper mapper;
  private final ProjectService service;
  private final ProjectValidator validator;

  @GetMapping("/projects")
  public List<Project> getAllProjects(
      @RequestParam(required = false) String ownerId,
      @RequestParam(required = false, defaultValue = "1") PageFromOne page,
      @RequestParam(required = false, defaultValue = "30") BoundedPageSize pageSize) {
    return service.getAllByOwnerId(ownerId, page, pageSize).stream().map(mapper::toRest).toList();
  }

  @GetMapping("/projects/{projectId}")
  public Project getProjectById(@PathVariable String projectId) {
    return mapper.toRest(service.getProjectById(projectId));
  }

  @PutMapping("/projects")
  public List<Project> crupdateProjects(@RequestBody List<Project> toCrupdate) {
    validator.accept(toCrupdate);
    return service.crupdateProjects(toCrupdate.stream().map(mapper::toDomain).toList()).stream()
        .map(mapper::toRest)
        .toList();
  }
}
