package org.hackaton.kotikota.endpoint.rest.controller.mapper;

import java.util.List;
import lombok.AllArgsConstructor;
import org.hackaton.kotikota.endpoint.rest.mapper.ProjectMapper;
import org.hackaton.kotikota.endpoint.rest.model.Contributor;
import org.hackaton.kotikota.endpoint.rest.model.ProjectContributors;
import org.hackaton.kotikota.repository.model.Project;
import org.hackaton.kotikota.repository.model.ProjectTransaction;
import org.hackaton.kotikota.repository.model.User;
import org.hackaton.kotikota.service.UserService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProjectContributorMapper {
  private final ProjectMapper projectMapper;
  private final UserService userService;

  public ProjectContributors toRest(Project project, List<ProjectTransaction> transactions) {
    ProjectContributors projectContributors = new ProjectContributors();
    projectContributors.project(projectMapper.toRest(project));
    projectContributors.contributors(transactions.stream().map(this::toRest).toList());
    return projectContributors;
  }

  public Contributor toRest(ProjectTransaction transaction) {
    User user = userService.getById(transaction.getUserId());
    return new Contributor()
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .donationAmount(transaction.getAmount());
  }
}
