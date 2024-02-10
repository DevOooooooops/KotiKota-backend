package org.hackaton.kotikota.endpoint.rest.controller.mapper;

import static org.hackaton.kotikota.repository.model.Transaction.TransactionType.INCOME;

import java.util.Objects;
import lombok.AllArgsConstructor;
import org.hackaton.kotikota.endpoint.rest.exception.BadRequestException;
import org.hackaton.kotikota.endpoint.rest.model.CreateProjectDonation;
import org.hackaton.kotikota.endpoint.rest.model.ProjectDonation;
import org.hackaton.kotikota.endpoint.rest.security.auth.AuthenticatedResourceProvider;
import org.hackaton.kotikota.repository.model.ProjectTransaction;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProjectTransactionMapper {
  private final AuthenticatedResourceProvider authenticatedResourceProvider;

  public ProjectDonation toRest(ProjectTransaction projectTransaction) {
    return new ProjectDonation()
        .id(projectTransaction.getId())
        .amount(projectTransaction.getAmount())
        .destination(projectTransaction.getProjectId())
        .creationDatetime(projectTransaction.getCreationDatetime())
        .reason(projectTransaction.getReason())
        .source(projectTransaction.getUserId());
  }

  public ProjectTransaction toIncome(CreateProjectDonation createProjectDonation) {
    String currentUserId = authenticatedResourceProvider.getAuthenticatedUser().getId();
    if (!Objects.equals(currentUserId, createProjectDonation.getSource())) {
      throw new BadRequestException("You can't create a transaction from somebody else's account");
    }
    return ProjectTransaction.projectTransactionBuilder()
        .projectId(createProjectDonation.getDestination())
        .userId(currentUserId)
        .type(INCOME)
        .amount(createProjectDonation.getAmount())
        .reason(createProjectDonation.getReason())
        .build();
  }
}
