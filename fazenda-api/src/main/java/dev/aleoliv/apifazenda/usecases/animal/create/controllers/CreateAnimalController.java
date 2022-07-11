package dev.aleoliv.apifazenda.usecases.animal.create.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.aleoliv.apifazenda.shared.exceptions.ServiceException;
import dev.aleoliv.apifazenda.usecases.animal.create.dtos.CreateAnimalRequestDTO;
import dev.aleoliv.apifazenda.usecases.animal.create.dtos.CreateAnimalResponseDTO;
import dev.aleoliv.apifazenda.usecases.animal.create.services.CreateAnimalService;

@RestController
@RequestMapping(value = "animal")
public class CreateAnimalController {

	private final CreateAnimalService service;

	public CreateAnimalController(CreateAnimalService service) {
		this.service = service;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public CreateAnimalResponseDTO handle(@RequestBody @Valid CreateAnimalRequestDTO dto) throws ServiceException {
		return service.execute(dto);
	}
}
