package dev.aleoliv.apifazenda.shared.http.controllers.advices.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AppValidDTO {

	private String target;
	private String message;

}
