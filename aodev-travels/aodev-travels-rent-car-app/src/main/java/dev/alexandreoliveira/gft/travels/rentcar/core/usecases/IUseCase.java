package dev.alexandreoliveira.gft.travels.rentcar.core.usecases;

import dev.alexandreoliveira.gft.travels.rentcar.core.dtos.OutputDTO;

public interface IUseCase<Input, Output> {

    OutputDTO<Output> execute(Input input);
}
