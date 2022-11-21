package dev.alexandreoliveira.mp.registrationservice.services.users.create;

import java.util.UUID;

public record UsersCreateServiceOutput(
  UUID id,
  String name,
  String email
) {
}
