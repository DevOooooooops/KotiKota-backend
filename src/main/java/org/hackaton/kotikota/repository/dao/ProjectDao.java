package org.hackaton.kotikota.repository.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.hackaton.kotikota.endpoint.rest.model.ProjectHealth;
import org.hackaton.kotikota.endpoint.rest.model.ProjectStatus;
import org.hackaton.kotikota.repository.model.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ProjectDao {
  private final EntityManager entityManager;

  public List<Project> findAllBy(
      String ownerId, String name, ProjectStatus status, ProjectHealth health, Pageable pageable) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Project> query = cb.createQuery(Project.class);
    Root<Project> root = query.from(Project.class);

    List<Predicate> predicates = new ArrayList<>();

    if (ownerId != null) {
      predicates.add(cb.equal(root.get("ownerId"), ownerId));
    }
    if (name != null) {
      predicates.add(
          cb.or(
              cb.like(cb.lower(root.get("name")), "%" + name + "%"),
              cb.like(root.get("name"), "%" + name + "%")));
    }
    if (status != null) {
      predicates.add(cb.equal(root.get("status"), ownerId));
    }
    if (health != null) {
      predicates.add(cb.equal(root.get("health"), ownerId));
    }

    query.where(predicates.toArray(Predicate[]::new));
    return entityManager
        .createQuery(query)
        .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
        .setMaxResults(pageable.getPageSize())
        .getResultList();
  }
}
