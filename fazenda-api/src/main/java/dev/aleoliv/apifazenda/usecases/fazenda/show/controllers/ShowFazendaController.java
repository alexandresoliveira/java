package dev.aleoliv.apifazenda.usecases.fazenda.show.controllers;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.aleoliv.apifazenda.shared.exceptions.ServiceException;
import dev.aleoliv.apifazenda.usecases.fazenda.show.dtos.ShowFazendaResponseDTO;
import dev.aleoliv.apifazenda.usecases.fazenda.show.services.ShowFazendaService;

@RestController
@RequestMapping(value = "fazenda")
public class ShowFazendaController {
	
	private static final Logger logger = LogManager.getLogger("fazendaLogger");

	private final ShowFazendaService service;

	public ShowFazendaController(ShowFazendaService service) {
		this.service = service;
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<ShowFazendaResponseDTO> handle() {
		logger.info("fazenda: handle = show all");
		return service.execute();
	}

	@GetMapping(value = "{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ShowFazendaResponseDTO handle(@PathVariable("id") UUID id) throws ServiceException {
		return service.execute(id);
	}
}
