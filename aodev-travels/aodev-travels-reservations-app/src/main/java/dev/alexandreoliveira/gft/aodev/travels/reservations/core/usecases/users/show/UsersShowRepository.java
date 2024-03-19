package dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.users.show;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.UserModel;

import java.util.UUID;

public interface UsersShowRepository {

    UserModel findUserById(UUID id);
}
