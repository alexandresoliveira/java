package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.services;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.DestinationModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.destinations.create.DestinationsCreateUseCase;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.destinations.index.DestinationsIndexUseCase;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.destinations.update.DestinationsUpdateUseCase;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities.DestinationEntity;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.repositories.destinations.ReadDestinationsRepository;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.destinations.create.DestinationsControllerCreateRequest;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.destinations.create.DestinationsControllerCreateResponse;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.destinations.index.DestinationsControllerIndexRequest;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.destinations.index.DestinationsControllerIndexResponse;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.destinations.update.DestinationsControllerUpdateRequest;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.events.kafka.destinations.DestinationsPublisherImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationsService extends BaseService {
    private final DestinationsCreateUseCase destinationsCreateUseCase;
    private final DestinationsUpdateUseCase destinationsUpdateUseCase;
    private final DestinationsIndexUseCase destinationsIndexUseCase;

    public DestinationsService(DestinationsPublisherImpl destinationsPublisher, ReadDestinationsRepository readDestinationsRepository) {
        this.destinationsCreateUseCase = new DestinationsCreateUseCase(destinationsPublisher);
        this.destinationsUpdateUseCase = new DestinationsUpdateUseCase(destinationsPublisher);
        this.destinationsIndexUseCase = new DestinationsIndexUseCase(readDestinationsRepository);
    }

    public void create(
            DestinationsControllerCreateRequest request) {
        DestinationEntity destinationEntity = new DestinationEntity();
        destinationEntity.setCity(request.city());
        destinationEntity.setState(request.state());
        destinationsCreateUseCase.execute(destinationEntity);
//        hasErrors(outputDTO, "Error occurs in create destination");
//        return new DestinationsControllerCreateResponse(
//                outputDTO.data().getId(),
//                outputDTO.data().getCity(),
//                outputDTO.data().getState());
    }

    public void update(
            DestinationsControllerUpdateRequest request) {
        DestinationEntity destinationEntity = new DestinationEntity();
        destinationEntity.setId(request.id());
        destinationEntity.setCity(request.city());
        destinationEntity.setState(request.state());
        destinationsUpdateUseCase.execute(destinationEntity);
    }

    public DestinationsControllerIndexResponse index(DestinationsControllerIndexRequest request) {
        var entity = new DestinationEntity();
        entity.setCity(request.term());
        entity.setState(request.term());
        OutputDTO<List<? extends DestinationModel>> output = destinationsIndexUseCase.execute(entity);
        hasErrors(output, "Error to recover list of destinations");
        List<DestinationsControllerIndexResponse.Destination> destinations = output.data().stream().map(DestinationsControllerIndexResponse.Destination::new).toList();
        return new DestinationsControllerIndexResponse(destinations);
    }
}
