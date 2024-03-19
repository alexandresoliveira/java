package dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.flights.index;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.FlightModel;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.entites.FlightEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FlightsIndexUseCaseTest {

    FlightsIndexRepository repository;

    @BeforeEach
    void beforeEach() {
        repository = new FlightsIndexRepositoryInMemory();
    }

    @ParameterizedTest
    @MethodSource("shouldExpectedErrorWhenModelIsInvalidSource")
    @Order(1)
    void shouldExpectedErrorWhenModelIsInvalid(FlightModel testModel, Integer numberOfExpectedErrors) {
        OutputDTO<List<? extends FlightModel>> output = new FlightsIndexUseCase(repository).execute(testModel);
        assertThat(output.errors()).isNotEmpty().hasSize(numberOfExpectedErrors);
    }

    static Stream<Arguments> shouldExpectedErrorWhenModelIsInvalidSource() {
        var test_1 = new FlightEntity();

        var test_2 = new FlightEntity();
        test_2.setOrigin("");

        var test_3 = new FlightEntity();
        test_3.setOrigin("");
        test_3.setDestiny("");

        var test_4 = new FlightEntity();
        test_4.setOrigin("asdf");
        test_4.setDestiny("");

        var test_5 = new FlightEntity();
        test_5.setOrigin("");
        test_5.setDestiny("asdf");

        return Stream.of(
                Arguments.of(test_1, 4),
                Arguments.of(test_2, 3),
                Arguments.of(test_3, 2),
                Arguments.of(test_4, 1),
                Arguments.of(test_5, 1)
        );
    }

    @Test
    @Order(2)
    void shouldExpectedNotFindAnyFlights() {
        var fakeEntity = new FlightEntity();
        fakeEntity.setId(UUID.randomUUID());
        fakeEntity.setOrigin("(CFN) Belo Horizonte, Minas Gerais");
        fakeEntity.setDestiny("(REC) Recife, Pernambuco");
        Map<UUID, FlightModel> fakeStore = Map.of(fakeEntity.getId(), fakeEntity);

        var testEntity = new FlightEntity();
        testEntity.setOrigin("Sao");
        testEntity.setDestiny("Esp");

        OutputDTO<List<? extends FlightModel>> output = new FlightsIndexUseCase(new FlightsIndexRepositoryInMemory(fakeStore)).execute(testEntity);

        assertThat(output.data()).isEmpty();
    }

    @Test
    @Order(3)
    void shouldExpectedToFindFlights() {
        var fakeEntity = new FlightEntity();
        fakeEntity.setId(UUID.randomUUID());
        fakeEntity.setOrigin("(CFN) Belo Horizonte, Minas Gerais");
        fakeEntity.setDestiny("(REC) Recife, Pernambuco");
        Map<UUID, FlightModel> fakeStore = Map.of(fakeEntity.getId(), fakeEntity);

        var testEntity = new FlightEntity();
        testEntity.setOrigin("Bel");
        testEntity.setDestiny("Rec");

        OutputDTO<List<? extends FlightModel>> output = new FlightsIndexUseCase(new FlightsIndexRepositoryInMemory(fakeStore)).execute(testEntity);

        assertThat(output.data()).isNotEmpty();
    }
}
