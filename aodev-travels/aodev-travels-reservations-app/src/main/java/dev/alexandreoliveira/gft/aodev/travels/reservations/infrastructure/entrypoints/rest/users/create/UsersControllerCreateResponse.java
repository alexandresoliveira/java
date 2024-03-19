package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.users.create;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.UserModel;

import java.util.UUID;

public record UsersControllerCreateResponse(
        UUID id,
        String name,
        String email
) {

    public UsersControllerCreateResponse(UserModel data) {
        this(data.getId(), data.getName(), data.getEmail());
    }
}
