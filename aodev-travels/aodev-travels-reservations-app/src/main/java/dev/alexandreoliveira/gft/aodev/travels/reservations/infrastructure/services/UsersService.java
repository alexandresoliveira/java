package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.services;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.UserModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.users.create.UsersCreateUseCase;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.users.show.UsersShowUseCase;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities.UserEntity;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.repositories.users.ReadUsersRepository;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.repositories.users.WriteUsersRepository;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.users.create.UsersControllerCreateRequest;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.users.create.UsersControllerCreateResponse;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.users.show.UsersControllerShowResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UsersService extends BaseService {

    private final UsersCreateUseCase usersCreateUseCase;
    private final UsersShowUseCase usersShowUseCase;

    public UsersService(WriteUsersRepository writeUsersRepository, ReadUsersRepository readUsersRepository) {
        this.usersCreateUseCase = new UsersCreateUseCase(writeUsersRepository);
        this.usersShowUseCase = new UsersShowUseCase(readUsersRepository);
    }

    public UsersControllerCreateResponse create(UsersControllerCreateRequest request) {
        var userEntity = new UserEntity();
        userEntity.setName(request.name());
        userEntity.setEmail(request.email());
        OutputDTO<UserModel> output = usersCreateUseCase.execute(userEntity);
        hasErrors(output, "Erro ao criar o usuário");
        return new UsersControllerCreateResponse(output.data());
    }

    public UsersControllerShowResponse show(UUID id) {
        OutputDTO<UserModel> output = usersShowUseCase.execute(id);
        hasErrors(output, "Erro ao recuperar o usuário");
        return new UsersControllerShowResponse(output.data());
    }
}
