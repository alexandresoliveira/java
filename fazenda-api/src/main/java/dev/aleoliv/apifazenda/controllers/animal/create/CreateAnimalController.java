package dev.aleoliv.apifazenda.controllers.animal.create;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.aleoliv.apifazenda.exceptions.ServiceException;
import dev.aleoliv.apifazenda.services.animal.create.AnimalCreateServiceInput;
import dev.aleoliv.apifazenda.services.animal.create.AnimalCreateServiceOutput;
import dev.aleoliv.apifazenda.services.animal.create.AnimalCreateService;

@RestController
@RequestMapping(value = "animal")
public class CreateAnimalController {

	private final AnimalCreateService service;

	public CreateAnimalController(AnimalCreateService service) {
		this.service = service;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public AnimalCreateServiceOutput handle(@RequestBody @Valid AnimalCreateServiceInput dto) throws ServiceException {
		return service.execute(dto);
	}
}
