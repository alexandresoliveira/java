package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.exceptions;

import java.util.Collections;
import java.util.List;

public class DataProvidersException extends RuntimeException {

    private final List<String> errors;

    public DataProvidersException(String message) {
        super(message);
        this.errors = Collections.emptyList();
    }

    public DataProvidersException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
