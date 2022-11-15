package dev.aleoliv.apifazenda.controllers.authentication.login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record AuthenticationLoginControllerRequest(
  @NotNull
  @NotEmpty
  @Email
  String email,

  @NotNull
  @NotEmpty
  @Size(min = 6)
  String password
) {
}
