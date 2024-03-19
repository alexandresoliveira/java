package dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.hotels.index;

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
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HotelsIndexUseCaseTest {

    HotelsIndexRepository repository;

    @BeforeEach
    void beforeEach() {
        repository = new HotelsIndexRepositoryInMemory();
    }

    @ParameterizedTest
    @MethodSource("shouldExpectedErrorWhenModelIsInvalidSource")
    @Order(1)
    void shouldExpectedErrorWhenModelIsInvalid(HotelModel testModel, Integer numberOfExpectedErrors) {
        OutputDTO<List<? extends HotelModel>> output = new HotelsIndexUseCase(repository).execute(testModel);
        assertThat(output.errors()).isNotEmpty().hasSize(numberOfExpectedErrors);
    }

    static Stream<Arguments> shouldExpectedErrorWhenModelIsInvalidSource() {
        var test_1 = new HotelEntity();

        var test_2 = new HotelEntity();
        test_2.setCity("");

        var test_3 = new HotelEntity();
        test_3.setCity("");
        test_3.setState("");

        var test_4 = new HotelEntity();
        test_4.setName("asdf");
        test_4.setCity("");

        var test_5 = new HotelEntity();
        test_5.setName("");
        test_5.setState("asdf");

        return Stream.of(
                Arguments.of(test_1, 6),
                Arguments.of(test_2, 5),
                Arguments.of(test_3, 4),
                Arguments.of(test_4, 3),
                Arguments.of(test_5, 3)
        );
    }

    @Test
    @Order(2)
    void shouldExpectedNotFindAnyFlights() {
        var fakeEntity = new HotelEntity();
        fakeEntity.setId(UUID.randomUUID());
        fakeEntity.setName("Hotel Top - Test");
        fakeEntity.setCity("Contegem");
        fakeEntity.setState("Minas Gerais");
        Map<UUID, HotelModel> fakeStore = Map.of(fakeEntity.getId(), fakeEntity);

        var testEntity = new HotelEntity();
        testEntity.setName("toperr");
        testEntity.setCity("toperr");
        testEntity.setState("toperr");

        OutputDTO<List<? extends HotelModel>> output = new HotelsIndexUseCase(new HotelsIndexRepositoryInMemory(fakeStore)).execute(testEntity);

        assertThat(output.data()).isEmpty();
    }

    @Test
    @Order(3)
    void shouldExpectedToFindFlights() {
        var fakeEntity = new HotelEntity();
        fakeEntity.setId(UUID.randomUUID());
        fakeEntity.setName("Hotel Top - Test");
        fakeEntity.setCity("Contegem");
        fakeEntity.setState("Minas Gerais");
        Map<UUID, HotelModel> fakeStore = Map.of(fakeEntity.getId(), fakeEntity);

        var testEntity = new HotelEntity();
        testEntity.setName("top");
        testEntity.setCity("top");
        testEntity.setState("top");

        OutputDTO<List<? extends HotelModel>> output = new HotelsIndexUseCase(new HotelsIndexRepositoryInMemory(fakeStore)).execute(testEntity);

        assertThat(output.data()).isNotEmpty();
    }
}
