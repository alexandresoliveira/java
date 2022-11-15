package dev.aleoliv.apifazenda.controllers.authentication.login;

public record AuthenticationLoginControllerResponse(
  String token,
  String tokenType
) {
}
