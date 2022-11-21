package dev.alexandreoliveira.mp.registrationservice.controllers.user.create;

import dev.alexandreoliveira.mp.registrationservice.services.users.create.UsersCreateServiceOutput;

import java.util.UUID;

record UserCreateControllerResponse(
  UUID id,
  String name,
  String email
) {

  UserCreateControllerResponse(UsersCreateServiceOutput output) {
    this(
      output.id(),
      output.name(),
      output.email()
    );
  }
}
