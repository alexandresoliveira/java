package dev.aleoliv.apifazenda.usecases.fazenda.delete.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.aleoliv.apifazenda.shared.exceptions.ServiceException;
import dev.aleoliv.apifazenda.usecases.fazenda.delete.services.DeleteFazendaService;

@RestController
@RequestMapping(value = "fazenda")
public class DeleteFazendaController {

	private final DeleteFazendaService service;

	public DeleteFazendaController(DeleteFazendaService service) {
		this.service = service;
	}

	@DeleteMapping(value = "{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void handle(@PathVariable("id") UUID id) throws ServiceException {
		service.execute(id);
	}
}
