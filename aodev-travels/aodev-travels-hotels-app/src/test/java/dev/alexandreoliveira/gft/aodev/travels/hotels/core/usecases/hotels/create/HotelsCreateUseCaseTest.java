package dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.hotels.create;

import dev.alexandreoliveira.gft.aodev.travels.hotels.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.models.HotelModel;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.dateproviders.postgresql.entites.HotelEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HotelsCreateUseCaseTest {

    HotelsCreateRepository repository;

    @BeforeEach
    void beforeEach() {
        repository = new HotelsCreateRepositoryInMemory();
    }

    @Test
    @Order(1)
    void shouldReturnErrorWhenModelHasWrong() {
        var testModel = new HotelEntity();
        OutputDTO<HotelModel> output = new HotelsCreateUseCase(repository).execute(testModel);
        assertThat(output).isNotNull();
        assertThat(output.errors()).isNotEmpty();
    }

    @ParameterizedTest
    @MethodSource("mustBeErrorWhenModelDoNotHaveAttributeSource")
    @Order(2)
    void mustBeErrorWhenModelDoNotHaveAttribute(HotelModel testModel, List<String> expectedMessage) {
        OutputDTO<HotelModel> output = new HotelsCreateUseCase(repository).execute(testModel);
        assertThat(output.errors()).isNotEmpty().hasSize(expectedMessage.size());
        assertThat(output.errors()).containsAll(expectedMessage);
    }

    static Stream<Arguments> mustBeErrorWhenModelDoNotHaveAttributeSource() {
        var testModel_1 = new HotelEntity();
        testModel_1.setCity("test");
        testModel_1.setName("a");

        var testModel_2 = new HotelEntity();
        testModel_2.setState("b");
        testModel_2.setName("b");

        return Stream.of(
                Arguments.of(testModel_1, List.of("state: must not be null (null)", "state: must not be empty (null)")),
                Arguments.of(testModel_2, List.of("city: must not be null (null)", "city: must not be empty (null)"))
        );
    }

    @Test
    @Order(3)
    void mustBeOkWhenModelIsCorrect() {
        var testModel = new HotelEntity();
        testModel.setName("test company");
        testModel.setCity("city test");
        testModel.setState("state test");

        OutputDTO<HotelModel> output = new HotelsCreateUseCase(repository).execute(testModel);

        assertThat(output.data()).isNotNull();
        assertThat(output.data().getId()).isNotNull();
    }

}
