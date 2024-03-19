package dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.users.create;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.UserModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.IUseCase;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.utils.validators.ModelValidatorUtil;

import java.util.List;

public record UsersCreateUseCase(
        UsersCreateRepository repository
) implements IUseCase.InOut<UserModel, UserModel> {

    @Override
    public OutputDTO<UserModel> execute(UserModel userModel) {
        List<String> errors = ModelValidatorUtil.isValid(userModel);
        if (!errors.isEmpty()) {
            return new OutputDTO<>(errors);
        }
        return new OutputDTO<>(repository.save(userModel));
    }
}
