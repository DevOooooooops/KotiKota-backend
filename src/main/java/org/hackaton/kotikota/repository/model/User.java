package org.hackaton.kotikota.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name = "\"user\"")
public class User implements Serializable {
  @Id private String id;
  private String firstName;
  private String lastName;
  private String firebaseId;

  @Column(unique = true)
  private String email;

  private LocalDate birthdate;
  private int balance;
}
