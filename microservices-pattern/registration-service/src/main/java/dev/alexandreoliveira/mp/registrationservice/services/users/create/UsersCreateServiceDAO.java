package dev.alexandreoliveira.mp.registrationservice.services.users.create;

import dev.alexandreoliveira.mp.registrationservice.database.jpa.entities.UserEntity;

import java.util.Optional;

public interface UsersCreateServiceDAO {

  UserEntity createUser(UserEntity userEntity);

  Optional<UserEntity> findByEmail(
    String email
  );
}
