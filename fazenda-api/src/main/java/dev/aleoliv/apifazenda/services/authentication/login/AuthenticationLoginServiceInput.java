package dev.aleoliv.apifazenda.services.authentication.login;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public record AuthenticationLoginServiceInput(
  @NotNull
  UUID traceErrorId,

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
