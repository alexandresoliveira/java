package dev.aleoliv.apifazenda.usecases.animal.show.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.aleoliv.apifazenda.shared.database.entities.AnimalEntity;
import dev.aleoliv.apifazenda.shared.database.repositories.AnimalRepository;
import dev.aleoliv.apifazenda.shared.exceptions.ServiceException;
import dev.aleoliv.apifazenda.usecases.animal.show.dtos.ShowAnimalResponseDTO;
import dev.aleoliv.apifazenda.usecases.animal.show.mappers.ShowAnimalMapper;

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
		throw new ServiceException("Entity not found!");
	}
}
