package org.hackaton.kotikota.service;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.hackaton.kotikota.repository.ProjectCommentRepository;
import org.hackaton.kotikota.repository.model.ProjectComment;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectCommentService {
  private final ProjectCommentRepository repository;

  public List<ProjectComment> getAllBy(String projectId) {
    return repository.findAllByProjectId(projectId);
  }

  public ProjectComment save(ProjectComment projectComment) {
    Optional<ProjectComment> optionalPersisted =
        repository.findByProjectIdAndAuthorId(
            projectComment.getProjectId(), projectComment.getAuthorId());
    return repository.save(
        optionalPersisted
            .map(
                o -> {
                  o.setNote(projectComment.getNote());
                  o.setContent(projectComment.getContent());
                  return o;
                })
            .orElse(projectComment));
  }
}
