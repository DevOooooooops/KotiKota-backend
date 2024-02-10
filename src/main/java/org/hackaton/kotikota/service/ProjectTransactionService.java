package org.hackaton.kotikota.service;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.hackaton.kotikota.endpoint.rest.exception.NotImplementedException;
import org.hackaton.kotikota.endpoint.rest.model.Money;
import org.hackaton.kotikota.repository.ProjectTransactionRepository;
import org.hackaton.kotikota.repository.dao.ProjectTransactionDao;
import org.hackaton.kotikota.repository.model.ProjectTransaction;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectTransactionService {
  private final ProjectTransactionRepository repository;
  private final ProjectTransactionDao dao;
  private final BankInfoService bankInfoService;

  public List<ProjectTransaction> donate(List<ProjectTransaction> projectTransactions) {
    if (projectTransactions.size() > 1) {
      throw new NotImplementedException("multiple donations at the same time is not supported yet");
    }
    ProjectTransaction projectTransaction = projectTransactions.get(0);
    bankInfoService.withdrawal(
        projectTransaction.getUserId(), new Money().amount(projectTransaction.getAmount()));
    return List.of(repository.save(projectTransaction));
  }

  public List<ProjectTransaction> getAllBy(
      String projectId, String userId, LocalDate beginDate, LocalDate endDate) {
    return dao.getAllBy(projectId, userId, beginDate, endDate);
  }

  public int countTotalDonationsBy(String projectId){
    return repository.countAllByProjectId(projectId);
  }
}
