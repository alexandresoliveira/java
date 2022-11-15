package dev.aleoliv.apifazenda.services.authentication.login;

public record AuthenticationLoginServiceOutput(
  String token,
  String type
) {
}
