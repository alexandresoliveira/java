package dev.aleoliv.apifazenda.usecases.fazenda.update.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.aleoliv.apifazenda.shared.database.entities.FazendaEntity;
import dev.aleoliv.apifazenda.shared.database.repositories.FazendaRepository;
import dev.aleoliv.apifazenda.shared.exceptions.ServiceException;
import dev.aleoliv.apifazenda.usecases.fazenda.update.dtos.UpdateFazendaRequestDTO;
import dev.aleoliv.apifazenda.usecases.fazenda.update.dtos.UpdateFazendaResponseDTO;
import dev.aleoliv.apifazenda.usecases.fazenda.update.mappers.UpdateFazendaMapper;

@Service
public class UpdateFazendaService {

	private final FazendaRepository repository;
	private final UpdateFazendaMapper mapper;

	public UpdateFazendaService(FazendaRepository repository, UpdateFazendaMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}
	
	public UpdateFazendaResponseDTO execute(UpdateFazendaRequestDTO dto) throws ServiceException {
		Optional<FazendaEntity> optionalEntity = repository.findById(dto.getId());
		if (optionalEntity.isPresent()) {
			FazendaEntity entity = optionalEntity.get();
			entity.setNome(dto.getNome());
			FazendaEntity fazendaEntity = repository.save(entity);
			return mapper.createDtoWith(fazendaEntity);
		}
		throw new ServiceException("Entity not found!");
	}
}
