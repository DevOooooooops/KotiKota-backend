package org.hackaton.kotikota.repository;

import java.util.List;
import org.hackaton.kotikota.repository.model.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
  @Query("""
  select p from Project p join ProjectTransaction pt on p.id = pt.projectId where pt.userId = ?1
""")
  List<Project> findAllWithDonationFrom(String userId);
}
