package dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases;

import dev.alexandreoliveira.gft.aodev.travels.hotels.core.dtos.OutputDTO;

public interface IUseCase<Input, Output> {

    OutputDTO<Output> execute(Input input);
}
