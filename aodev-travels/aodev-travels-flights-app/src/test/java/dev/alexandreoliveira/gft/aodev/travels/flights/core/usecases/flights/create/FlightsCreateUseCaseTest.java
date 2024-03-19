package dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.flights.create;

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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FlightsCreateUseCaseTest {

    FlightsCreateRepository repository;

    @BeforeEach
    void beforeEach() {
        repository = new FlightsCreateRepositoryInMemory();
    }

    @Test
    @Order(1)
    void shouldReturnErrorWhenModelHasWrong() {
        var testModel = new FlightEntity();
        OutputDTO<FlightModel> output = new FlightsCreateUseCase(repository).execute(testModel);
        assertThat(output).isNotNull();
        assertThat(output.errors()).isNotEmpty();
    }

    @ParameterizedTest
    @MethodSource("mustBeErrorWhenFlightDoNotHaveAttributeSource")
    @Order(2)
    void mustBeErrorWhenFlightDoNotHaveAttribute(FlightModel testModel, List<String> expectedMessage) {
        OutputDTO<FlightModel> output = new FlightsCreateUseCase(repository).execute(testModel);
        assertThat(output.errors()).isNotEmpty().hasSize(expectedMessage.size());
        assertThat(output.errors()).containsAll(expectedMessage);
    }

    static Stream<Arguments> mustBeErrorWhenFlightDoNotHaveAttributeSource() {
        var testModel_1 = new FlightEntity();
        testModel_1.setCompany("test");
        testModel_1.setFlightNumber(123);
        testModel_1.setOrigin("a");
        testModel_1.setDestiny("b");
        testModel_1.setCheckIn(LocalDateTime.now());
        testModel_1.setPrice(BigDecimal.ZERO);

        var testModel_2 = new FlightEntity();
        testModel_2.setFlightNumber(123);
        testModel_2.setOrigin("a");
        testModel_2.setDestiny("b");
        testModel_2.setCheckIn(LocalDateTime.now());
        testModel_2.setCheckOut(LocalDateTime.now());
        testModel_2.setPrice(BigDecimal.ZERO);

        return Stream.of(
                Arguments.of(testModel_1, List.of("checkOut: must not be null (null)")),
                Arguments.of(testModel_2, List.of("company: must not be null (null)", "company: must not be empty (null)"))
        );
    }

    @Test
    @Order(3)
    void mustBeOkWhenModelIsCorrect() {
        var testModel = new FlightEntity();
        testModel.setCompany("test company");
        testModel.setFlightNumber(123);
        testModel.setOrigin("a");
        testModel.setDestiny("b");
        testModel.setCheckIn(LocalDateTime.now());
        testModel.setCheckOut(LocalDateTime.now());
        testModel.setPrice(BigDecimal.ZERO);

        OutputDTO<FlightModel> output = new FlightsCreateUseCase(repository).execute(testModel);

        assertThat(output.data()).isNotNull();
        assertThat(output.data().getId()).isNotNull();
    }

}
