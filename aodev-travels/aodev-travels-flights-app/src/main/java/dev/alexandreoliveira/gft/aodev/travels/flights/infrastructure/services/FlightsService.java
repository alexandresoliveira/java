package dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.services;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.FlightModel;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.flights.create.FlightsCreateUseCase;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.flights.index.FlightsIndexUseCase;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.seats.create.SeatsCreateUseCase;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.entites.FlightEntity;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.entites.SeatEntity;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.repositories.SeatsRepository;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.repositories.flights.ReadFlightsRepository;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.repositories.flights.WriteFlightsRepository;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.rest.flights.create.FlightsControllerCreateRequest;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.rest.flights.create.FlightsControllerCreateResponse;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.rest.flights.index.FlightsControllerIndexRequest;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.rest.flights.index.FlightsControllerIndexResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional(rollbackFor = {Throwable.class})
public class FlightsService extends BaseService {

    private final FlightsCreateUseCase flightsCreateUseCase;
    private final FlightsIndexUseCase flightsIndexUseCase;
    private final SeatsCreateUseCase seatsCreateUseCase;

    public FlightsService(WriteFlightsRepository writeFlightsRepository, ReadFlightsRepository readFlightsRepository, SeatsRepository seatsRepository) {
        this.flightsCreateUseCase = new FlightsCreateUseCase(writeFlightsRepository);
        this.flightsIndexUseCase = new FlightsIndexUseCase(readFlightsRepository);
        this.seatsCreateUseCase = new SeatsCreateUseCase(seatsRepository);
    }

    public FlightsControllerCreateResponse create(FlightsControllerCreateRequest request) {
        var entity = new FlightEntity();
        entity.setCompany(request.company());
        entity.setFlightNumber(request.flightNumber());
        entity.setOrigin(request.from());
        entity.setDestiny(request.to());
        entity.setCheckIn(request.checkIn());
        entity.setCheckOut(request.checkOut());
        entity.setPrice(request.price());

        OutputDTO<FlightModel> output = flightsCreateUseCase.execute(entity);
        hasErrors(output, "Erro ao cadastrar um novo voo!");

        var flight = output.data();

        request.seats().forEach(seat -> {
            var seatEntity = new SeatEntity();
            seatEntity.setFlight(flight);
            seatEntity.setSeatNumber(seat.seatNumber());
            seatsCreateUseCase.execute(seatEntity);
        });

        return new FlightsControllerCreateResponse(output.data().getId());
    }

    public FlightsControllerIndexResponse index(FlightsControllerIndexRequest request) {
        var entity = new FlightEntity();
        entity.setOrigin(StringUtils.hasText(request.cityOrigin()) ? "%" + request.cityOrigin() + "%" : null);
        entity.setDestiny(StringUtils.hasText(request.cityDestiny()) ? "%" + request.cityDestiny() + "%" : request.cityDestiny() );
        OutputDTO<List<? extends FlightModel>> output = flightsIndexUseCase.execute(entity);

        hasErrors(output, "Erro ao recuperar voos");

        List<FlightsControllerIndexResponse.Flight> flights = output.data().stream().map(FlightsControllerIndexResponse.Flight::new).toList();
        return new FlightsControllerIndexResponse(flights);
    }
}
