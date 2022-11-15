package dev.aleoliv.apifazenda.services.authentication.login;

import dev.aleoliv.apifazenda.configurations.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component("authenticationLoginServiceDAOImpl")
public class AuthenticationLoginServiceDAOImpl implements AuthenticationLoginServiceDAO {
  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;

  public AuthenticationLoginServiceDAOImpl(
    AuthenticationManager authenticationManager,
    TokenService tokenService
  ) {
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }

  @Override
  public String authenticateUser(
    String email,
    String password
  ) {
    var data = new UsernamePasswordAuthenticationToken(
      email,
      password
    );

    var authenticate = authenticationManager.authenticate(data);

    return tokenService.buildToken(authenticate);
  }
}
