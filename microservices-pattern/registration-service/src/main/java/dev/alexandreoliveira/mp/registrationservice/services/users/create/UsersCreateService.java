package dev.alexandreoliveira.mp.registrationservice.services.users.create;

import dev.alexandreoliveira.mp.registrationservice.database.jpa.entities.UserEntity;
import dev.alexandreoliveira.mp.registrationservice.services.AppService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

@Service("UsersCreateService")
public class UsersCreateService implements AppService<UsersCreateServiceInput, UsersCreateServiceOutput> {

  private final UsersCreateServiceDAO dao;
  private final Validator validator;

  public UsersCreateService(
    @Qualifier("UsersCreateServiceDAOImpl")
    UsersCreateServiceDAO dao,
    Validator validator
  ) {
    this.dao = dao;
    this.validator = validator;
  }

  @Override
  public UsersCreateServiceOutput execute(UsersCreateServiceInput input) {
    Set<ConstraintViolation<UsersCreateServiceInput>> constraintViolations = validator.validate(input);
    if (!constraintViolations.isEmpty()) {
      throw new ConstraintViolationException(constraintViolations);
    }

    if (dao.findByEmail(input.email()).isPresent()) {
      throw new RuntimeException("Found user with this email " + input.email());
    }

    UserEntity userEntity = new UserEntity();
    userEntity.setName(input.name());
    userEntity.setEmail(input.email());
    userEntity.setPassword(input.password());

    UserEntity savedUserEntity = dao.createUser(userEntity);

    return new UsersCreateServiceOutput(
      savedUserEntity.getId(),
      savedUserEntity.getName(),
      savedUserEntity.getEmail()
    );
  }
}
