package dev.aleoliv.apifazenda.services.fazenda.show.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.aleoliv.apifazenda.database.jpa.entities.FarmEntity;
import dev.aleoliv.apifazenda.database.jpa.repositories.FarmRepository;
import dev.aleoliv.apifazenda.exceptions.ServiceException;
import dev.aleoliv.apifazenda.services.fazenda.show.dtos.ShowFazendaResponseDTO;
import dev.aleoliv.apifazenda.services.fazenda.show.mappers.ShowFazendaMapper;

@Service
public class ShowFazendaService {

	private final FarmRepository repository;
	private final ShowFazendaMapper mapper;

	public ShowFazendaService(FarmRepository repository, ShowFazendaMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	public List<ShowFazendaResponseDTO> execute() {
		List<FarmEntity> all = repository.findAll();
		return mapper.convertListDtoWith(all);
	}

	public ShowFazendaResponseDTO execute(UUID id) throws ServiceException {
		Optional<FarmEntity> optionalEntity = repository.findById(id);
		if (optionalEntity.isPresent()) {
			return mapper.convertDtoWith(optionalEntity.get());
		}
		throw new ServiceException("Fazenda not found!",
      traceLoggerId);
	}
}
