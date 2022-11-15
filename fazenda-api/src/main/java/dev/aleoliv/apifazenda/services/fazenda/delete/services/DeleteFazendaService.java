package dev.aleoliv.apifazenda.services.fazenda.delete.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.aleoliv.apifazenda.database.jpa.entities.FarmEntity;
import dev.aleoliv.apifazenda.database.jpa.repositories.FarmRepository;
import dev.aleoliv.apifazenda.exceptions.ServiceException;

@Service
public class DeleteFazendaService {

	private final FarmRepository repository;

	public DeleteFazendaService(FarmRepository repository) {
		this.repository = repository;
	}
	
	public void execute(UUID id) throws ServiceException {
		Optional<FarmEntity> optional = repository.findById(id);
		if (!optional.isPresent()) {
			throw new ServiceException("Fazenda not found",
        traceLoggerId);
		}
		repository.delete(optional.get());
	}
}
