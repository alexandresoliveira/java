package dev.aleoliv.apifazenda.usecases.fazenda.create.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.aleoliv.apifazenda.shared.exceptions.ServiceException;
import dev.aleoliv.apifazenda.usecases.fazenda.create.dtos.CreateFazendaRequestDTO;
import dev.aleoliv.apifazenda.usecases.fazenda.create.dtos.CreateFazendaResponseDTO;
import dev.aleoliv.apifazenda.usecases.fazenda.create.services.CreateFazendaService;

@RestController
@RequestMapping(value = "fazenda")
public class CreateFazendaController {

	private final Logger log = LoggerFactory.getLogger(CreateFazendaController.class);

	private final CreateFazendaService service;

	public CreateFazendaController(CreateFazendaService service) {
		this.service = service;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public CreateFazendaResponseDTO handle(@RequestBody @Valid CreateFazendaRequestDTO dto) throws ServiceException {
		log.info("CreateFazendaController::handle");
		return service.execute(dto);
	}
}
