package dev.aleoliv.apifazenda.usecases.animal.update.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.aleoliv.apifazenda.shared.exceptions.ServiceException;
import dev.aleoliv.apifazenda.usecases.animal.update.dtos.UpdateAnimalRequestDTO;
import dev.aleoliv.apifazenda.usecases.animal.update.dtos.UpdateAnimalResponseDTO;
import dev.aleoliv.apifazenda.usecases.animal.update.services.UpdateAnimalService;

@RestController
@RequestMapping(value = "animal")
public class UpdateAnimalController {

	private final UpdateAnimalService service;

	public UpdateAnimalController(UpdateAnimalService service) {
		this.service = service;
	}
	
	@PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public UpdateAnimalResponseDTO handle(@RequestBody @Valid UpdateAnimalRequestDTO dto) throws ServiceException {
		return service.execute(dto);
	}
}
