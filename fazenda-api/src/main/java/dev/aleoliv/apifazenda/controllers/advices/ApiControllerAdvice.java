package dev.aleoliv.apifazenda.controllers.advices;

import dev.aleoliv.apifazenda.controllers.advices.responses.MethodArgumentNotValidExceptionResponse;
import dev.aleoliv.apifazenda.controllers.advices.responses.ServiceExceptionResponse;
import dev.aleoliv.apifazenda.exceptions.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedList;
import java.util.List;

@RestControllerAdvice
public class ApiControllerAdvice {

  @ExceptionHandler(ServiceException.class)
  public ResponseEntity<ServiceExceptionResponse> handle(ServiceException e) {
    ServiceExceptionResponse body = new ServiceExceptionResponse();
    body.setMessage(e.getMessage());
    return ResponseEntity.badRequest().body(body);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<MethodArgumentNotValidExceptionResponse>> handle(MethodArgumentNotValidException e) {
    List<MethodArgumentNotValidExceptionResponse> body = new LinkedList<>();
    for (FieldError fe : e.getBindingResult().getFieldErrors()) {
      MethodArgumentNotValidExceptionResponse valid = new MethodArgumentNotValidExceptionResponse();
      valid.setTarget(fe.getField());
      valid.setMessage(fe.getDefaultMessage());
      body.add(valid);
    }
    return ResponseEntity.badRequest().body(body);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<String> handle(
    BadCredentialsException e
  ) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong credentials, try again!");
  }
}
