package dev.aleoliv.apifazenda.usecases.animal.storelist.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.aleoliv.apifazenda.usecases.animal.storelist.dtos.StoreListAnimalRequestDTO;
import dev.aleoliv.apifazenda.usecases.animal.storelist.dtos.StoreListAnimalResponseDTO;
import dev.aleoliv.apifazenda.usecases.animal.storelist.services.StoreListAnimalService;

@RestController
@RequestMapping(value = "animal/store/list")
public class StoreListAnimalController {
	
	private StoreListAnimalService service;

	public StoreListAnimalController(StoreListAnimalService service) {
		this.service = service;
	}
	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public List<StoreListAnimalResponseDTO> handle(@RequestBody @Valid List<StoreListAnimalRequestDTO> dtos) {
		return service.execute(dtos);
	}
}
