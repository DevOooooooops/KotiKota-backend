package org.hackaton.kotikota.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.hackaton.kotikota.endpoint.rest.controller.mapper.ProjectContributorMapper;
import org.hackaton.kotikota.endpoint.rest.model.ProjectContributors;
import org.hackaton.kotikota.repository.model.Project;
import org.hackaton.kotikota.repository.model.ProjectTransaction;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectContributorService {
  private final ProjectService projectService;
  private final ProjectTransactionService projectTransactionService;
  private final ProjectContributorMapper projectContributorMapper;

  public ProjectContributors getProjectContributors(String projectId) {
    Project project = projectService.getProjectById(projectId);
    List<ProjectTransaction> transactions = projectTransactionService.getAllBy(projectId);
    return projectContributorMapper.toRest(project, transactions);
  }
}
