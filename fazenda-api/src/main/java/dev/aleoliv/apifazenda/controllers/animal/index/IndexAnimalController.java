package dev.aleoliv.apifazenda.controllers.animal.index;

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

import dev.aleoliv.apifazenda.exceptions.ServiceException;
import dev.aleoliv.apifazenda.services.animal.index.dtos.ShowAnimalResponseDTO;
import dev.aleoliv.apifazenda.services.animal.index.services.ShowAnimalService;

@RestController
@RequestMapping(value = "animal")
public class IndexAnimalController {
	
	private static final Logger logger = LogManager.getLogger("animalLogger");

	private final ShowAnimalService service;

	public IndexAnimalController(ShowAnimalService service) {
		this.service = service;
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<ShowAnimalResponseDTO> handle() {
		logger.info("animal: handle = show all");
		return service.execute();
	}

	@GetMapping(value = "{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ShowAnimalResponseDTO handle(@PathVariable("id") UUID id) throws ServiceException {
		return service.execute(id);
	}
}
