package dev.aleoliv.apifazenda.shared.http.controllers.advices;

import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.aleoliv.apifazenda.shared.exceptions.ServiceException;
import dev.aleoliv.apifazenda.shared.http.controllers.advices.entities.AppServiceEntity;
import dev.aleoliv.apifazenda.shared.http.controllers.advices.entities.AppValidDTO;

@RestControllerAdvice
public class BaseControllerAdvice {

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<AppServiceEntity> handlerServiceException(ServiceException e) {
		AppServiceEntity body = new AppServiceEntity();
		body.setMessage(e.getMessage());
		return new ResponseEntity<AppServiceEntity>(body, HttpStatus.BAD_REQUEST);
	}

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
		List<AppValidDTO> body = new LinkedList<>();
		if (e.getBindingResult() != null) {
			for (FieldError fe : e.getBindingResult().getFieldErrors()) {
				AppValidDTO valid = new AppValidDTO();
				valid.setTarget(fe.getField());
				valid.setMessage(fe.getDefaultMessage());
				body.add(valid);
			}
		}
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
}
