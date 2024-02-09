package org.hackaton.kotikota.repository.model;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static org.hibernate.type.SqlTypes.NAMED_ENUM;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@MappedSuperclass
public class Transaction {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private String id;

  private int amount;
  private String userId;

  @Enumerated(STRING)
  @JdbcTypeCode(NAMED_ENUM)
  private TransactionType type;

  private String reason;
  @CreationTimestamp private Instant creationDatetime;

  public enum TransactionType {
    INCOME("INCOME"),
    OUTCOME("OUTCOME");

    private String value;

    private TransactionType(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return this.value;
    }

    public String toString() {
      return String.valueOf(this.value);
    }

    @JsonCreator
    public static TransactionType fromValue(String value) {
      TransactionType[] var1 = values();
      int var2 = var1.length;

      for (int var3 = 0; var3 < var2; ++var3) {
        TransactionType b = var1[var3];
        if (b.value.equals(value)) {
          return b;
        }
      }

      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }
}
