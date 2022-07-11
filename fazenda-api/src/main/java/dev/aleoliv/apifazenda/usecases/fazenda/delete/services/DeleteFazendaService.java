package dev.aleoliv.apifazenda.usecases.fazenda.delete.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.aleoliv.apifazenda.shared.database.entities.FazendaEntity;
import dev.aleoliv.apifazenda.shared.database.repositories.FazendaRepository;
import dev.aleoliv.apifazenda.shared.exceptions.ServiceException;

@Service
public class DeleteFazendaService {

	private final FazendaRepository repository;

	public DeleteFazendaService(FazendaRepository repository) {
		this.repository = repository;
	}
	
	public void execute(UUID id) throws ServiceException {
		Optional<FazendaEntity> optional = repository.findById(id);
		if (!optional.isPresent()) {
			throw new ServiceException("Fazenda not found");
		}
		repository.delete(optional.get());
	}
}
