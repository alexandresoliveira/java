package dev.aleoliv.apifazenda.usecases.animal.delete.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.aleoliv.apifazenda.shared.database.entities.AnimalEntity;
import dev.aleoliv.apifazenda.shared.database.repositories.AnimalRepository;
import dev.aleoliv.apifazenda.shared.exceptions.ServiceException;

@Service
public class DeleteAnimalService {

	private final AnimalRepository repository;

	public DeleteAnimalService(AnimalRepository repository) {
		this.repository = repository;
	}
	
	public void execute(UUID id) throws ServiceException {
		Optional<AnimalEntity> optional = repository.findById(id);
		if (!optional.isPresent()) {
			throw new ServiceException("Animal not found!");
		}
		repository.delete(optional.get());
	}
}
