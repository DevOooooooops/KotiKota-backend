package org.hackaton.kotikota.repository;

import java.util.Optional;
import org.hackaton.kotikota.repository.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByEmail(String email);
}
