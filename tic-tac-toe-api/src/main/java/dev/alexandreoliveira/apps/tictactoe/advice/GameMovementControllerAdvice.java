package dev.alexandreoliveira.apps.tictactoe.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GameMovementControllerAdvice {


  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, List<String>>> handle(MethodArgumentNotValidException e) {
    var errors = new HashMap<String, List<String>>();
    e
      .getFieldErrors()
      .stream()
      .forEach(err -> {
        if (errors.containsKey(err.getField())) {
          errors
            .get(err.getField())
            .add(err.getDefaultMessage());
        } else {
          errors.put(
            err.getField(),
            Arrays.asList(err.getDefaultMessage())
          );
        }
      });
    return ResponseEntity
      .badRequest()
      .body(errors);
  }
}
