package dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.services;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.services.exceptions.ServiceException;

class BaseService {

    void hasErrors(OutputDTO<?> output, String message) {
        if (!output.errors().isEmpty()) {
            throw new ServiceException(message, output.errors());
        }
    }
}
