package org.hackaton.kotikota.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"project_transaction\"")
@Getter
@Setter
public class ProjectTransaction extends Transaction {
  private String projectId;

  @Builder(builderMethodName = "projectTransactionBuilder")
  public ProjectTransaction(
      String id,
      int amount,
      String userId,
      TransactionType type,
      String reason,
      Instant creationDatetime,
      String projectId) {
    super(id, amount, userId, type, reason, creationDatetime);
    this.projectId = projectId;
  }
}
