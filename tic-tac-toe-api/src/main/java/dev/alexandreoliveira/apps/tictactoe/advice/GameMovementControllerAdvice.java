package dev.alexandreoliveira.apps.tictactoe.advice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.alexandreoliveira.apps.tictactoe.service.exception.GameMovementException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GameMovementControllerAdvice {

	@ExceptionHandler(GameMovementException.class)
	public ResponseEntity<Map<String, String>> handle(GameMovementException e) {
		Map<String, String> response = Map.of("msg", e.getMessage());
		return ResponseEntity.badRequest().body(response);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, List<String>>> handle(MethodArgumentNotValidException e) {
		var errors = new HashMap<String, List<String>>();
		e.getFieldErrors()
		 .stream()
		 .forEach(err -> {
			 if (errors.containsKey(err.getField())) {
				 errors.get(err.getField()).add(err.getDefaultMessage());
			 } else {
				 errors.put(err.getField(), Arrays.asList(err.getDefaultMessage()));
			 }
		 });
		return ResponseEntity.badRequest().body(errors);
	}
}
