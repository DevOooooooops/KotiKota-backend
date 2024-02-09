package org.hackaton.kotikota.repository;

import java.util.List;
import org.hackaton.kotikota.repository.model.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
  List<Project> getAllByOwnerId(String id, Pageable pageable);
}
