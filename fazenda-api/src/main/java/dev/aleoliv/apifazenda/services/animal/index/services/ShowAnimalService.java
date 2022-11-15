package dev.aleoliv.apifazenda.services.animal.index.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.aleoliv.apifazenda.database.jpa.entities.AnimalEntity;
import dev.aleoliv.apifazenda.database.jpa.repositories.AnimalRepository;
import dev.aleoliv.apifazenda.exceptions.ServiceException;
import dev.aleoliv.apifazenda.services.animal.index.dtos.ShowAnimalResponseDTO;
import dev.aleoliv.apifazenda.services.animal.index.mappers.ShowAnimalMapper;

@Service
public class ShowAnimalService {

	private final AnimalRepository repository;
	private final ShowAnimalMapper mapper;

	public ShowAnimalService(AnimalRepository repository, ShowAnimalMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}
	
	public List<ShowAnimalResponseDTO> execute() {
		List<AnimalEntity> animals = repository.findAll();
		return mapper.convertToListDtoWith(animals);
	}
	
	public ShowAnimalResponseDTO execute(UUID id) throws ServiceException {
		Optional<AnimalEntity> optional = repository.findById(id);
		if (optional.isPresent()) {
			return mapper.convertToDtoWith(optional.get());
		}
		throw new ServiceException("Entity not found!",
      traceLoggerId);
	}
}
