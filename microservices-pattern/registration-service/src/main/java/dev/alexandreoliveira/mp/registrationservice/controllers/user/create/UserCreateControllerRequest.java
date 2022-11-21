package dev.alexandreoliveira.mp.registrationservice.controllers.user.create;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

record UserCreateControllerRequest(
  @NotNull
  @NotEmpty
  @Size(min = 5, max = 100)
  String name,

  @NotNull
  @NotEmpty
  @Email
  @Size(min = 5, max = 100)
  String email,

  @NotNull
  @NotEmpty
  @Size(min = 8, max = 100)
  String password
) {
}
