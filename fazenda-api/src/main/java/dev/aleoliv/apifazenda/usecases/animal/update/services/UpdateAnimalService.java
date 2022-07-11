package dev.aleoliv.apifazenda.usecases.animal.update.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.aleoliv.apifazenda.shared.database.entities.AnimalEntity;
import dev.aleoliv.apifazenda.shared.database.repositories.AnimalRepository;
import dev.aleoliv.apifazenda.shared.exceptions.ServiceException;
import dev.aleoliv.apifazenda.usecases.animal.update.dtos.UpdateAnimalRequestDTO;
import dev.aleoliv.apifazenda.usecases.animal.update.dtos.UpdateAnimalResponseDTO;
import dev.aleoliv.apifazenda.usecases.animal.update.mappers.UpdateAnimalMapper;

@Service
public class UpdateAnimalService {

	private final AnimalRepository repository;
	private final UpdateAnimalMapper mapper;

	public UpdateAnimalService(AnimalRepository repository, UpdateAnimalMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	public UpdateAnimalResponseDTO execute(UpdateAnimalRequestDTO dto) throws ServiceException {
		Optional<AnimalEntity> optional = repository.findById(dto.getId());
		if (optional.isPresent()) {
			AnimalEntity animalEntity = optional.get();
			animalEntity.setTag(dto.getTag());
			animalEntity.getFazenda().setId(dto.getIdFazenda());
			AnimalEntity entity = repository.save(animalEntity);
			return mapper.createDtoWith(entity);
		}
		throw new ServiceException("Animal not found!");
	}
}
