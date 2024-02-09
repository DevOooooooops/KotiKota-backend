package org.hackaton.kotikota.endpoint.rest.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.hackaton.kotikota.endpoint.rest.mapper.UserMapper;
import org.hackaton.kotikota.endpoint.rest.model.CreateUser;
import org.hackaton.kotikota.endpoint.rest.model.User;
import org.hackaton.kotikota.endpoint.rest.model.UserProfile;
import org.hackaton.kotikota.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
  private final UserMapper mapper;

  private final UserService userService;

  @GetMapping("/users")
  public List<User> getAllUsers() {
    return userService.getAll().stream().map(mapper::toRest).toList();
  }

  @GetMapping("/users/{userId}")
  public User getUserById(@PathVariable String userId) {
    return mapper.toRest(userService.getById(userId));
  }

  @PostMapping("/users")
  public User createUser(@RequestBody CreateUser createUser) {
    return mapper.toRest(userService.save(mapper.toDomain(createUser)));
  }

  @PutMapping("/users/{userId}/profile")
  public User updateUserProfile(@PathVariable String userId, @RequestBody UserProfile userProfile) {
    return mapper.toRest(userService.save(mapper.toDomain(userProfile, userId)));
  }
}
