package dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.users.create;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.UserModel;

public interface UsersCreateRepository {

    UserModel save(UserModel model);
}
