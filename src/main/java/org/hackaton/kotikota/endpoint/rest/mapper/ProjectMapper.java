package org.hackaton.kotikota.endpoint.rest.mapper;

import java.util.Objects;
import lombok.AllArgsConstructor;
import org.hackaton.kotikota.endpoint.rest.model.Project;
import org.hackaton.kotikota.endpoint.rest.model.CreateProject;
import org.hackaton.kotikota.service.ProjectTransactionService;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ProjectMapper {
  private final ProjectTransactionService projectTransactionService;
  public Project toRest(org.hackaton.kotikota.repository.model.Project domain) {
    return new Project()
        .id(domain.getId())
        .name(domain.getName())
        .description(domain.getDescription())
        .ownerId(domain.getOwnerId())
        .deadline(domain.getDeadline())
        .status(domain.getStatus())
        .health(domain.getHealth())
        .targetAmount(domain.getTargetAmount())
        .totalDonations(projectTransactionService.countTotalDonationsBy(domain.getId()));
  }

  public org.hackaton.kotikota.repository.model.Project toDomain(CreateProject rest) {
    return org.hackaton.kotikota.repository.model.Project.builder()
        .id(rest.getId())
        .name(rest.getName())
        .description(rest.getDescription())
        .targetAmount(Objects.isNull(rest.getTargetAmount()) ? 0 : rest.getTargetAmount())
        .deadline(rest.getDeadline())
        .status(rest.getStatus())
        .health(rest.getHealth())
        .ownerId(rest.getOwnerId())
        .build();
  }
}
