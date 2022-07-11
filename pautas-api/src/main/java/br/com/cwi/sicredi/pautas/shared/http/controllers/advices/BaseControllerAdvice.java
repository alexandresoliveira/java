package br.com.cwi.sicredi.pautas.shared.http.controllers.advices;

import br.com.cwi.sicredi.pautas.shared.exceptions.ServiceApiException;
import br.com.cwi.sicredi.pautas.shared.http.controllers.advices.dtos.HttpMessageNotReadableExceptionResponseDTO;
import br.com.cwi.sicredi.pautas.shared.http.controllers.advices.dtos.MethodArgumentNotValidExceptionResponseDTO;
import br.com.cwi.sicredi.pautas.shared.http.controllers.advices.dtos.MethodArgumentTypeMismatchExceptionResponseDTO;
import br.com.cwi.sicredi.pautas.shared.http.controllers.advices.dtos.ServiceApiExceptionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.LinkedList;
import java.util.List;

@RestControllerAdvice
public class BaseControllerAdvice {

    @ExceptionHandler(ServiceApiException.class)
    public ResponseEntity<ServiceApiExceptionResponseDTO> handlerServiceException(ServiceApiException e) {
        ServiceApiExceptionResponseDTO response = new ServiceApiExceptionResponseDTO(e.getMessage());
        return new ResponseEntity<ServiceApiExceptionResponseDTO>(response, e.getHttpStatus());
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<MethodArgumentNotValidExceptionResponseDTO> body = new LinkedList<>();
        if (e.getBindingResult() != null) {
            for (FieldError fe : e.getBindingResult().getFieldErrors()) {
                MethodArgumentNotValidExceptionResponseDTO valid =
                        new MethodArgumentNotValidExceptionResponseDTO(fe.getField(), fe.getDefaultMessage());
                body.add(valid);
            }
        }
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HttpMessageNotReadableExceptionResponseDTO> handleHttpMessageNotReadable(
            HttpMessageNotReadableException e) {
        HttpMessageNotReadableExceptionResponseDTO response = new HttpMessageNotReadableExceptionResponseDTO(
                e.getLocalizedMessage());
        return new ResponseEntity<HttpMessageNotReadableExceptionResponseDTO>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<MethodArgumentTypeMismatchExceptionResponseDTO> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException e) {
        MethodArgumentTypeMismatchExceptionResponseDTO response = new MethodArgumentTypeMismatchExceptionResponseDTO(
                e.getLocalizedMessage());
        return new ResponseEntity<MethodArgumentTypeMismatchExceptionResponseDTO>(response, HttpStatus.BAD_REQUEST);
    }
}
