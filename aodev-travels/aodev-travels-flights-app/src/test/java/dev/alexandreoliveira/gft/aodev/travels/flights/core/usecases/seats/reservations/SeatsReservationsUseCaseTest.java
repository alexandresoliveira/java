package dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.seats.reservations;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.SeatModel;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.entites.FlightEntity;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.entites.SeatEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SeatsReservationsUseCaseTest {

    private static final UUID FLIGHT_ID_1 = UUID.fromString("7e9c5ae8-b888-47ed-bb78-80b39061c808");
    private static final UUID FLIGHT_ID_2 = UUID.fromString("0c2463ef-e4d8-4569-8b9e-d17d77eb4392");

    SeatsReservationsPublisher publisher;

    @BeforeEach
    void beforeEach() {
        publisher = new SeatsReservationsPublisherFake();
    }

    @Test
    @Order(1)
    void shouldExpectedErrorsWhenSeatIsInvalid() {
        var testEntity = new SeatEntity();
        OutputDTO<List<SeatModel>> output = new SeatsReservationsUseCase(new SeatsReservationsRepositoryInMemory(HashMap.newHashMap(0)), publisher).execute(List.of(testEntity));
        assertThat(output.errors()).isNotEmpty();
    }

    @Test
    @Order(2)
    void shouldExpectedErrorWhenSeatHasReserved() {
        var fakeExternalId = UUID.randomUUID();

        var flightEntity = new FlightEntity();
        flightEntity.setId(FLIGHT_ID_1);

        var fakeEntity = new SeatEntity();
        fakeEntity.setId(UUID.randomUUID());
        fakeEntity.setFlight(flightEntity);
        fakeEntity.setSeatNumber("01a");
        fakeEntity.setExternalId(fakeExternalId);

        Map<UUID, SeatModel> fakeStore = Map.of(fakeEntity.getId(), fakeEntity);

        var testEntiity = new SeatEntity();
        testEntiity.setFlight(flightEntity);
        testEntiity.setSeatNumber("01a");
        testEntiity.setExternalId(UUID.randomUUID());

        OutputDTO<List<SeatModel>> output = new SeatsReservationsUseCase(new SeatsReservationsRepositoryInMemory(fakeStore), publisher).execute(List.of(testEntiity));
        assertThat(output.errors()).hasSize(1);
        assertThat(output.errors().getFirst()).isEqualTo("Error to reserve a seat. This seat has reserved.");
    }

    @ParameterizedTest
    @MethodSource("mustBeExpectedTheSeatsInFlightAreReservedSource")
    @Order(3)
    void mustBeExpectedTheSeatsInFlightAreReserved(SeatModel expectedModel) {
        var testModel = new SeatEntity();
        testModel.setId(UUID.randomUUID());
        testModel.setFlight(expectedModel.getFlight());
        testModel.setSeatNumber(expectedModel.getSeatNumber());

        ((SeatEntity) expectedModel).setId(testModel.getId());

        Map<UUID, SeatModel> fakeStore = HashMap.newHashMap(1);
        fakeStore.put(testModel.getId(), testModel);

        OutputDTO<List<SeatModel>> output = new SeatsReservationsUseCase(new SeatsReservationsRepositoryInMemory(fakeStore), publisher).execute(List.of(expectedModel));

        assertThat(output.data()).isNotEmpty();
    }

    static Stream<? extends SeatModel> mustBeExpectedTheSeatsInFlightAreReservedSource() {
        final UUID externalId = UUID.randomUUID();

        var flightEntity_1 = new FlightEntity();
        flightEntity_1.setId(FLIGHT_ID_1);

        var flightEntity_2 = new FlightEntity();
        flightEntity_2.setId(FLIGHT_ID_2);

        var test1 = new SeatEntity();
        test1.setFlight(flightEntity_1);
        test1.setSeatNumber("01a");
        test1.setExternalId(externalId);

        var test2 = new SeatEntity();
        test2.setFlight(flightEntity_1);
        test2.setSeatNumber("01b");
        test2.setExternalId(externalId);

        var test3 = new SeatEntity();
        test3.setFlight(flightEntity_2);
        test3.setSeatNumber("01a");
        test3.setExternalId(externalId);

        var test4 = new SeatEntity();
        test4.setFlight(flightEntity_2);
        test4.setSeatNumber("01b");
        test4.setExternalId(externalId);

        return Stream.of(
                test1,
                test2,
                test3,
                test4
        );
    }
}
