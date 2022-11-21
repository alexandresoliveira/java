package dev.alexandreoliveira.mp.registrationservice.services.users.create;

import dev.alexandreoliveira.mp.registrationservice.database.jpa.entities.UserEntity;
import dev.alexandreoliveira.mp.registrationservice.database.jpa.repositories.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("UsersCreateServiceDAOImpl")
class UsersCreateServiceDAOImpl implements UsersCreateServiceDAO {

  private final UserRepository userRepository;

  public UsersCreateServiceDAOImpl(
    UserRepository userRepository
  ) {
    this.userRepository = userRepository;
  }

  @Override
  public UserEntity createUser(UserEntity userEntity) {
    return userRepository.save(userEntity);
  }

  @Override
  public Optional<UserEntity> findByEmail(String email) {
    return userRepository.findByEmailEquals(email);
  }
}
