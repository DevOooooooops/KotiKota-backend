package org.hackaton.kotikota.endpoint.rest.controller;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.hackaton.kotikota.endpoint.rest.controller.mapper.ProjectTransactionMapper;
import org.hackaton.kotikota.endpoint.rest.model.CreateProjectDonation;
import org.hackaton.kotikota.endpoint.rest.model.ProjectDonation;
import org.hackaton.kotikota.service.ProjectTransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ProjectDonationController {
  private final ProjectTransactionService transactionService;
  private final ProjectTransactionMapper mapper;

  @GetMapping("/projects/{projectId}/donations")
  public List<ProjectDonation> getTransactionsByProjectId(
      @PathVariable String projectId,
      @RequestParam(required = false) String source,
      @RequestParam(required = false) LocalDate beginDate,
      @RequestParam(required = false) LocalDate endDate) {
    return transactionService.getAllBy(projectId, source, beginDate, endDate).stream()
        .map(mapper::toRest)
        .toList();
  }

  @PostMapping("/projects/{projectId}/donations")
  public List<ProjectDonation> donate(
      @PathVariable String projectId, @RequestBody List<CreateProjectDonation> payload) {
    return transactionService.donate(payload.stream().map(mapper::toIncome).toList()).stream()
        .map(mapper::toRest)
        .toList();
  }
}
