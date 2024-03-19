package dev.alexandreoliveira.gft.travels.rentcar.infrastructure.services;

import dev.alexandreoliveira.gft.travels.rentcar.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.travels.rentcar.infrastructure.services.exceptions.ServiceException;

class BaseService {

    void hasErrors(OutputDTO<?> output, String message) {
        if (!output.errors().isEmpty()) {
            throw new ServiceException(message, output.errors());
        }
    }
}
