package dev.alexandreoliveira.mp.registrationservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class ApiControllerAdvice {

  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  @ResponseBody
  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<GenericError> handleException(MethodArgumentNotValidException exception) {
    List<Map<String, String>> errors = exception
      .getFieldErrors()
      .stream()
      .map(fieldError -> Map.of(
        "field",
        fieldError.getField(),
        "message",
        Objects.requireNonNullElse(
          fieldError.getDefaultMessage(),
          "Invalid field"
        )
      ))
      .toList();

    return ResponseEntity.badRequest()
                         .body(new GenericError(
                           "error",
                           stackTrace(exception),
                           errors
                         ));
  }

  private List<Map<String, String>> stackTrace(Exception exception) {
    return Arrays
      .stream(exception.getStackTrace())
      .filter(stackTraceElement -> stackTraceElement.getClassName().startsWith("dev.alexandreoliveira"))
      .map(stackTraceElement -> Map.of(
        "class",
        stackTraceElement.getClassName(),
        "lineNumber",
        String.valueOf(stackTraceElement.getLineNumber())
      ))
      .toList();
  }

  record GenericError(
    String message,
    List<Map<String, String>> stackTrace,
    List<Map<String, String>> errors
  ) {
  }
}
