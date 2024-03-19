package dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.seats.create;

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
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SeatsCreateUseCaseTest {

    SeatsCreateRepository repository;

    @BeforeEach
    void beforeEach() {
        repository = new SeatsCreateRepositoryInMemory();
    }

    @Test
    @Order(1)
    void shouldExpectedErrorsWhenDataIsInvalid() {
        var testEntity = new SeatEntity();
        OutputDTO<SeatModel> output = new SeatsCreateUseCase(repository).execute(testEntity);
        assertThat(output.errors()).isNotEmpty();
    }

    @ParameterizedTest
    @MethodSource("shouldExpectCorrectErrorMessageSource")
    @Order(2)
    void shouldExpectCorrectErrorMessage(SeatModel testModel, List<String> expectedMessages) {
        OutputDTO<SeatModel> output = new SeatsCreateUseCase(repository).execute(testModel);
        assertThat(output.errors()).hasSize(expectedMessages.size());
        assertThat(output.errors()).containsAll(expectedMessages);
    }

    static Stream<Arguments> shouldExpectCorrectErrorMessageSource() {
        var test_1 = new SeatEntity();
        test_1.setSeatNumber("01a");

        var flightEntity = new FlightEntity();
        flightEntity.setId(UUID.randomUUID());

        var test_2 = new SeatEntity();
        test_2.setFlight(flightEntity);

        return Stream.of(
                Arguments.of(test_1, List.of("flight: must not be null (null)")),
                Arguments.of(test_2, List.of("seatNumber: must not be null (null)", "seatNumber: must not be empty (null)"))
        );
    }

    @Test
    @Order(3)
    void mustBeOkWhenModelIsCorrect() {
        var flightEntity = new FlightEntity();
        flightEntity.setId(UUID.randomUUID());

        var testEntity = new SeatEntity();
        testEntity.setFlight(flightEntity);
        testEntity.setSeatNumber("01a");
        OutputDTO<SeatModel> output = new SeatsCreateUseCase(repository).execute(testEntity);
        assertThat(output.data()).isNotNull();
    }
}
