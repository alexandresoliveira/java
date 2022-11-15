package dev.aleoliv.apifazenda.services.fazenda.update.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.aleoliv.apifazenda.database.jpa.entities.FarmEntity;
import dev.aleoliv.apifazenda.database.jpa.repositories.FarmRepository;
import dev.aleoliv.apifazenda.exceptions.ServiceException;
import dev.aleoliv.apifazenda.services.fazenda.update.dtos.UpdateFazendaRequestDTO;
import dev.aleoliv.apifazenda.services.fazenda.update.dtos.UpdateFazendaResponseDTO;
import dev.aleoliv.apifazenda.services.fazenda.update.mappers.UpdateFazendaMapper;

@Service
public class UpdateFazendaService {

	private final FarmRepository repository;
	private final UpdateFazendaMapper mapper;

	public UpdateFazendaService(FarmRepository repository, UpdateFazendaMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}
	
	public UpdateFazendaResponseDTO execute(UpdateFazendaRequestDTO dto) throws ServiceException {
		Optional<FarmEntity> optionalEntity = repository.findById(dto.getId());
		if (optionalEntity.isPresent()) {
			FarmEntity entity = optionalEntity.get();
			entity.setNome(dto.getNome());
			FarmEntity farmEntity = repository.save(entity);
			return mapper.createDtoWith(farmEntity);
		}
		throw new ServiceException("Entity not found!",
      traceLoggerId);
	}
}
