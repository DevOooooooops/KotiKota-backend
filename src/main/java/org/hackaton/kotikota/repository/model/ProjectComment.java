package org.hackaton.kotikota.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"project_comment\"")
@Getter
@Builder
@Setter
public class ProjectComment {
    @Id private String id;
    private String projectId;
    private String authorId;
    private int note;
    private String content;
}
