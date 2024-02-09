package org.hackaton.kotikota.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.hackaton.kotikota.endpoint.rest.exception.NotFoundException;
import org.hackaton.kotikota.repository.UserRepository;
import org.hackaton.kotikota.repository.model.User;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository repository;

  public List<User> getAll() {
    return repository.findAll();
  }

  public User getById(String id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("User.Id = " + id + " not found."));
  }

  public User getByEmail(String email) {
    return repository
        .findByEmail(email)
        .orElseThrow(() -> new NotFoundException("User.Email = " + email + " not found."));
  }

  public User save(User user) {
    return repository.save(user);
  }
}
