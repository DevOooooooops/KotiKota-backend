package org.hackaton.kotikota.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.hackaton.kotikota.endpoint.rest.exception.NotFoundException;
import org.hackaton.kotikota.endpoint.rest.model.ProjectHealth;
import org.hackaton.kotikota.endpoint.rest.model.ProjectStatus;
import org.hackaton.kotikota.model.BoundedPageSize;
import org.hackaton.kotikota.model.PageFromOne;
import org.hackaton.kotikota.repository.ProjectRepository;
import org.hackaton.kotikota.repository.dao.ProjectDao;
import org.hackaton.kotikota.repository.model.Project;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectService {
  private final ProjectRepository repository;
  private final ProjectDao dao;

  public Project getProjectById(String id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Project id." + id + " not found."));
  }

  public List<Project> crupdateProjects(List<Project> toCreate) {
    return repository.saveAll(toCreate);
  }

  public List<Project> getAllBy(
      String ownerId,
      String name,
      ProjectStatus status,
      ProjectHealth health,
      PageFromOne page,
      BoundedPageSize pageSize) {
    Pageable pageable = PageRequest.of(page.getValue() - 1, pageSize.getValue());
    return dao.findAllBy(ownerId, name, status, health, pageable);
  }

  public List<Project> getAllWithDonationFrom(String userId) {
    return repository.findAllWithDonationFrom(userId);
  }
}
