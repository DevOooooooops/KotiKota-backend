package org.hackaton.kotikota.repository.model;

import static jakarta.persistence.EnumType.STRING;
import static org.hibernate.type.SqlTypes.NAMED_ENUM;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hackaton.kotikota.endpoint.rest.model.ProjectHealth;
import org.hackaton.kotikota.endpoint.rest.model.ProjectStatus;
import org.hibernate.annotations.JdbcTypeCode;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name = "\"project\"")
public class Project {
  @Id private String id;
  private String name;
  private String description;

  @Column(name = "target_amount")
  private int targetAmount;

  private Instant deadline;

  @Column(name = "owner_id")
  private String ownerId;

  @Enumerated(STRING)
  @JdbcTypeCode(NAMED_ENUM)
  private ProjectStatus status;

  @Enumerated(STRING)
  @JdbcTypeCode(NAMED_ENUM)
  private ProjectHealth health;
}
