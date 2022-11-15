package dev.aleoliv.apifazenda.controllers.advices.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MethodArgumentNotValidExceptionResponse {

	private String target;
	private String message;

}
