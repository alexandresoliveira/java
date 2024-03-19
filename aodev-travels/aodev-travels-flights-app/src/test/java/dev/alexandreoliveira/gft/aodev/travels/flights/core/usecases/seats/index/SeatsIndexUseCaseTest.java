package dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.seats.index;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.SeatModel;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.entites.FlightEntity;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.entites.SeatEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SeatsIndexUseCaseTest {

    SeatsIndexRepository repository;

    @BeforeEach
    void beforeEach() {
        repository = new SeatsIndexRepositoryInMemory();
    }

    @Test
    @Order(1)
    void shouldExpectErrorWhenFlightIdIsNotPresent() {
        var testEntity = new SeatEntity();
        OutputDTO<List<? extends SeatModel>> output = new SeatsIndexUseCase(repository).execute(testEntity);
        assertThat(output.errors()).isNotEmpty();
    }

    @Test
    @Order(2)
    void shouldToCheckErrorMessageWhenFlightIdIsNotPresent() {
        String expectErrorMessage = "flight: must not be null (null)";
        var testEntity = new SeatEntity();
        OutputDTO<List<? extends SeatModel>> output = new SeatsIndexUseCase(repository).execute(testEntity);
        assertThat(output.errors()).isNotEmpty();
        assertThat(output.errors()).containsExactly(expectErrorMessage);
    }

    @Test
    @Order(3)
    void mustBeNotReturnAnySeatForFlight() {
        var flightEntity = new FlightEntity();
        flightEntity.setId(UUID.randomUUID());

        var testEntity = new SeatEntity();
        testEntity.setFlight(flightEntity);
        OutputDTO<List<? extends SeatModel>> output = new SeatsIndexUseCase(repository).execute(testEntity);
        assertThat(output.data()).isEmpty();
    }

    @Test
    @Order(4)
    void mustBeReturnSeatsForFlight() {
        var flightEntity = new FlightEntity();
        flightEntity.setId(UUID.randomUUID());

        var fakeEntity = new SeatEntity();
        fakeEntity.setId(UUID.randomUUID());
        fakeEntity.setFlight(flightEntity);
        fakeEntity.setSeatNumber("01b");

        Map<UUID, SeatModel> fakeStore = Map.of(fakeEntity.getId(), fakeEntity);

        var testEntity = new SeatEntity();
        testEntity.setFlight(fakeEntity.getFlight());

        OutputDTO<List<? extends SeatModel>> output = new SeatsIndexUseCase(new SeatsIndexRepositoryInMemory(fakeStore)).execute(testEntity);

        assertThat(output.data()).isNotEmpty();
    }
}
