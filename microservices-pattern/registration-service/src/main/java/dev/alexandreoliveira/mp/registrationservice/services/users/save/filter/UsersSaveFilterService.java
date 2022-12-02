package dev.alexandreoliveira.mp.registrationservice.services.users.save.filter;

import dev.alexandreoliveira.mp.registrationservice.services.AppService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

@Service("UsersSaveFilterService")
public class UsersSaveFilterService implements AppService<UsersSaveFilterServiceInput, UsersSaveFilterServiceOutput> {

  private final UsersSaveFilterServiceDAO dao;
  private final Validator validator;

  public UsersSaveFilterService(
    @Qualifier("UsersSaveFilterServiceDAOImpl")
    UsersSaveFilterServiceDAO dao,
    Validator validator
  ) {
    this.dao = dao;
    this.validator = validator;
  }

  @Override
  public UsersSaveFilterServiceOutput execute(UsersSaveFilterServiceInput usersSaveFilterServiceInput) {
    return null;
  }
}
