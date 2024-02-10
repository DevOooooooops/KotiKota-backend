package org.hackaton.kotikota.endpoint.rest.controller.mapper;

import lombok.AllArgsConstructor;
import org.hackaton.kotikota.endpoint.rest.model.ProjectComment;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProjectCommentMapper {
    public ProjectComment toRest(org.hackaton.kotikota.repository.model.ProjectComment domain) {
        return new ProjectComment()
                .id(domain.getId())
                .projectId(domain.getProjectId())
                .content(domain.getContent())
                .note(domain.getNote());
    }

    public org.hackaton.kotikota.repository.model.ProjectComment toDomain(ProjectComment rest) {
        return org.hackaton.kotikota.repository.model.ProjectComment.builder()
                .id(rest.getId())
                .projectId(rest.getProjectId())
                .content(rest.getContent())
                .note(rest.getNote())
                .build();
    }
}
