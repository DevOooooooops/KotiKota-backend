package org.hackaton.kotikota.endpoint.rest.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.hackaton.kotikota.endpoint.rest.controller.mapper.ProjectCommentMapper;
import org.hackaton.kotikota.endpoint.rest.model.ProjectComment;
import org.hackaton.kotikota.service.ProjectCommentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ProjectCommentController {
  private final ProjectCommentService service;
  private final ProjectCommentMapper mapper;

  @GetMapping("/projects/{projectId}/comments")
  public List<ProjectComment> getAllBy(@PathVariable String projectId) {
    return service.getAllBy(projectId).stream().map(mapper::toRest).toList();
  }

  @PutMapping("/projects/{projectId}/comments")
  public ProjectComment commentProject(
      @PathVariable String projectId, @RequestBody ProjectComment projectComment) {
    return mapper.toRest(service.save(mapper.toDomain(projectComment)));
  }
}
