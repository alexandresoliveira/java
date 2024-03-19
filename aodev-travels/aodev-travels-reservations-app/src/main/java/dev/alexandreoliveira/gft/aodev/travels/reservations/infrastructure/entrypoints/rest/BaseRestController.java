package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest;

import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.exceptions.DataProvidersException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class BaseRestController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Map<String, Object>>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e
    ) {
        List<Map<String, Object>> body =
                e.getBindingResult().getFieldErrors().stream()
                        .map(
                                fe -> Map.<String, Object>of(
                                        "status",
                                        HttpStatus.BAD_REQUEST.value(),
                                        "cause",
                                        e.getClass().getSimpleName(),
                                        "field",
                                        fe.getField(),
                                        "message",
                                        Objects.requireNonNull(fe.getDefaultMessage())
                                )
                        )
                        .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(DataProvidersException.class)
    public ResponseEntity<String> handleDataProvidersException(
            DataProvidersException e
    ) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
