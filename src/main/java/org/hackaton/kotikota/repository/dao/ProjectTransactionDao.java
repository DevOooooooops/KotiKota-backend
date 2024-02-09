package org.hackaton.kotikota.repository.dao;

import static java.time.LocalTime.MAX;
import static java.time.ZoneOffset.UTC;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.hackaton.kotikota.repository.model.ProjectTransaction;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ProjectTransactionDao {
  private final EntityManager entityManager;

  public List<ProjectTransaction> getAllBy(
      String projectId, String userId, LocalDate beginDate, LocalDate endDate) {
    assert (projectId != null) : "projectId is mandatory";
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<ProjectTransaction> query = cb.createQuery(ProjectTransaction.class);
    Root<ProjectTransaction> root = query.from(ProjectTransaction.class);

    List<Predicate> predicates = new ArrayList<>();

    predicates.add(cb.equal(root.get("projectId"), projectId));
    if (userId != null) {
      predicates.add(cb.equal(root.get("userId"), userId));
    }

    if (beginDate != null) {
      Predicate startingDatePredicate =
          cb.greaterThanOrEqualTo(
              root.get("creationDatetime"), beginDate.atStartOfDay().toInstant(UTC));
      predicates.add(startingDatePredicate);
    }

    if (endDate != null) {
      Predicate endingDatePredicate =
          cb.lessThanOrEqualTo(root.get("creationDatetime"), endDate.atTime(MAX).toInstant(UTC));
      predicates.add(endingDatePredicate);
    }

    query.where(predicates.toArray(Predicate[]::new));

    return entityManager.createQuery(query).getResultList();
  }
}
