package org.hackaton.kotikota.endpoint.rest.mapper;

import static java.util.UUID.randomUUID;

import lombok.AllArgsConstructor;
import org.hackaton.kotikota.endpoint.rest.model.BankInfo;
import org.hackaton.kotikota.endpoint.rest.model.CreateUser;
import org.hackaton.kotikota.endpoint.rest.model.User;
import org.hackaton.kotikota.endpoint.rest.model.UserProfile;
import org.hackaton.kotikota.service.UserService;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {
  private final UserService userService;

  public User toRest(org.hackaton.kotikota.repository.model.User user) {
    return new User()
        .id(user.getId())
        .firebaseId(user.getFirebaseId())
        .profile(
            new UserProfile()
                .birthdate(user.getBirthdate())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .profilePicture(user.getProfilePicture()))
        .bankInfo(new BankInfo().amount(user.getBalance()));
  }

  public User toRestMasked(org.hackaton.kotikota.repository.model.User user) {
    return new User()
        .id(user.getId())
        .firebaseId(user.getFirebaseId())
        .profile(
            new UserProfile()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .profilePicture(user.getProfilePicture()))
        .bankInfo(null);
  }

  public org.hackaton.kotikota.repository.model.User toDomain(CreateUser createUser) {
    return org.hackaton.kotikota.repository.model.User.builder()
        .id(randomUUID().toString())
        .firebaseId(createUser.getFirebaseId())
        .build();
  }

  public org.hackaton.kotikota.repository.model.User toDomain(UserProfile profile, String userId) {
    var persisted = userService.getById(userId);
    persisted.setEmail(profile.getEmail());
    persisted.setFirstName(profile.getFirstName());
    persisted.setLastName(profile.getLastName());
    persisted.setBirthdate(profile.getBirthdate());
    persisted.setProfilePicture(profile.getProfilePicture());
    return persisted;
  }
}
