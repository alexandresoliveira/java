package dev.aleoliv.apifazenda.usecases.fazenda.show.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.aleoliv.apifazenda.shared.database.entities.FazendaEntity;
import dev.aleoliv.apifazenda.shared.database.repositories.FazendaRepository;
import dev.aleoliv.apifazenda.shared.exceptions.ServiceException;
import dev.aleoliv.apifazenda.usecases.fazenda.show.dtos.ShowFazendaResponseDTO;
import dev.aleoliv.apifazenda.usecases.fazenda.show.mappers.ShowFazendaMapper;

@Service
public class ShowFazendaService {

	private final FazendaRepository repository;
	private final ShowFazendaMapper mapper;

	public ShowFazendaService(FazendaRepository repository, ShowFazendaMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	public List<ShowFazendaResponseDTO> execute() {
		List<FazendaEntity> all = repository.findAll();
		return mapper.convertListDtoWith(all);
	}

	public ShowFazendaResponseDTO execute(UUID id) throws ServiceException {
		Optional<FazendaEntity> optionalEntity = repository.findById(id);
		if (optionalEntity.isPresent()) {
			return mapper.convertDtoWith(optionalEntity.get());
		}
		throw new ServiceException("Fazenda not found!");
	}
}
