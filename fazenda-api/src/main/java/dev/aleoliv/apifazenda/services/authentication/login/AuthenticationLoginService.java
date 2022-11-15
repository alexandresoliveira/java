package dev.aleoliv.apifazenda.services.authentication.login;

import dev.aleoliv.apifazenda.exceptions.ServiceException;
import dev.aleoliv.apifazenda.services.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("authenticationLoginService")
public class AuthenticationLoginService implements ApiService<AuthenticationLoginServiceInput, AuthenticationLoginServiceOutput> {
  private static final Logger logger = LoggerFactory.getLogger(AuthenticationLoginService.class);

  private final AuthenticationLoginServiceDAO dao;

  public AuthenticationLoginService(
    @Qualifier("authenticationLoginServiceDAOImpl")
    AuthenticationLoginServiceDAO dao
  ) {
    this.dao = dao;
  }

  @Override
  public AuthenticationLoginServiceOutput execute(
    AuthenticationLoginServiceInput input
  ) {
    try {
      logger.info(
        "{} AuthenticationLoginService :: execute",
        input.traceErrorId()
      );

      String token = dao.authenticateUser(
        input.email(),
        input.password()
      );

      return new AuthenticationLoginServiceOutput(
        token,
        "Bearer"
      );
    } catch (Exception e) {
      logger.error(
        "{} AuthenticationLoginService :: execute :: exception :: getMessage-{}, getStackTrace-{}",
        input.traceErrorId(),
        e.getMessage(),
        e.getStackTrace()
      );

      throw new ServiceException(
        "Erro no processo de autenticação, verifique os logs com o traceErrorId.",
        input.traceErrorId()
      );
    }
  }
}
