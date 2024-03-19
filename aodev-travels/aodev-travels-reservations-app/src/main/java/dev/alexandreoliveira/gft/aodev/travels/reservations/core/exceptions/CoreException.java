package dev.alexandreoliveira.gft.aodev.travels.reservations.core.exceptions;

import java.util.Collections;
import java.util.List;

public class CoreException extends RuntimeException {

    private final List<String> errors;
    private final String useCase;

    public CoreException(List<String> errors, String useCase, String message) {
        super(message);
        this.errors = errors;
        this.useCase = useCase;
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public String getUseCase() {
        return useCase;
    }
}
