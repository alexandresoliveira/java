package dev.aleoliv.apifazenda.usecases.fazenda.update.controllers;

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
import dev.aleoliv.apifazenda.usecases.fazenda.update.dtos.UpdateFazendaRequestDTO;
import dev.aleoliv.apifazenda.usecases.fazenda.update.dtos.UpdateFazendaResponseDTO;
import dev.aleoliv.apifazenda.usecases.fazenda.update.services.UpdateFazendaService;

@RestController
@RequestMapping(value = "fazenda")
public class UpdateFazendaController {

	private final UpdateFazendaService service;

	public UpdateFazendaController(UpdateFazendaService service) {
		this.service = service;
	}

	@PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public UpdateFazendaResponseDTO handle(@RequestBody @Valid UpdateFazendaRequestDTO dto) throws ServiceException {
		return service.execute(dto);
	}
}
