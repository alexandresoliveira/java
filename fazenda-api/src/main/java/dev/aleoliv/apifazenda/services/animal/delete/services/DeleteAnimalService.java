package dev.aleoliv.apifazenda.services.animal.delete.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.aleoliv.apifazenda.database.jpa.entities.AnimalEntity;
import dev.aleoliv.apifazenda.database.jpa.repositories.AnimalRepository;
import dev.aleoliv.apifazenda.exceptions.ServiceException;

@Service
public class DeleteAnimalService {

	private final AnimalRepository repository;

	public DeleteAnimalService(AnimalRepository repository) {
		this.repository = repository;
	}
	
	public void execute(UUID id) throws ServiceException {
		Optional<AnimalEntity> optional = repository.findById(id);
		if (!optional.isPresent()) {
			throw new ServiceException("Animal not found!",
        traceLoggerId);
		}
		repository.delete(optional.get());
	}
}
