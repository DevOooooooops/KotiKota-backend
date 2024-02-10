package org.hackaton.kotikota.repository;

import java.time.Instant;
import java.util.List;
import org.hackaton.kotikota.repository.model.ProjectTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTransactionRepository extends JpaRepository<ProjectTransaction, String> {
  List<ProjectTransaction> findAllByProjectId(String projectId);
  int countAllByProjectId(String projectId);
}
