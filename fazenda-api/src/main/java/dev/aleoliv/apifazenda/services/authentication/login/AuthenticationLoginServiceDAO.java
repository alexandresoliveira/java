package dev.aleoliv.apifazenda.services.authentication.login;

public interface AuthenticationLoginServiceDAO {

  String authenticateUser(String email, String password);
}
