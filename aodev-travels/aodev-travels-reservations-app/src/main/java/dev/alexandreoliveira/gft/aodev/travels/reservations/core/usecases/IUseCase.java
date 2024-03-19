package dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.dtos.OutputDTO;

public interface IUseCase {

    interface InOut<Input, Output> {
        OutputDTO<Output> execute(Input input);
    }

    interface In<Input> {
        void execute(Input input);
    }
}
