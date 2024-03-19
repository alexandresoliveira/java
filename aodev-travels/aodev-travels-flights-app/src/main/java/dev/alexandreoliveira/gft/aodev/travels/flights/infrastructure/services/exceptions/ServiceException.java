package dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.services.exceptions;

import java.util.Collections;
import java.util.List;

public class ServiceException extends RuntimeException {

    private List<String> errors;

    public ServiceException(List<String> errors) {
        super("Erro ao executar o serviço");
        this.errors = errors;
    }

    public ServiceException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public ServiceException(String message, Throwable throwable, List<String> errors) {
        super(message, throwable);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
