package org.hackaton.kotikota.repository;

import java.util.List;
import java.util.Optional;
import org.hackaton.kotikota.repository.model.ProjectComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectCommentRepository extends JpaRepository<ProjectComment, String> {
  List<ProjectComment> findAllByProjectId(String projectId);

  Optional<ProjectComment> findByProjectIdAndAuthorId(String projectId, String authorId);
}
