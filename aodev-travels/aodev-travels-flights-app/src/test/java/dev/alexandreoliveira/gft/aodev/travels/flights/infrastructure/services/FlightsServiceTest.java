package dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.services;

import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.entites.FlightEntity;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.entites.SeatEntity;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.repositories.SeatsRepository;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.repositories.flights.ReadFlightsRepository;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.repositories.flights.WriteFlightsRepository;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.rest.flights.create.FlightsControllerCreateRequest;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.rest.flights.create.FlightsControllerCreateResponse;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.rest.flights.index.FlightsControllerIndexRequest;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.rest.flights.index.FlightsControllerIndexResponse;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.services.exceptions.ServiceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FlightsServiceTest {

    private AutoCloseable closeable;

    @Mock
    WriteFlightsRepository mockWriteFlightsRepository;

    @Mock
    ReadFlightsRepository mockReadFlightsRepository;

    @Mock
    SeatsRepository mockSeatsRepository;

    @BeforeEach
    void beforeEach() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void afterEach() throws Exception {
        closeable.close();
    }

    @Test
    @Order(1)
    void whenCreateShouldExpectedErrorWhenDataIsIncorrect() {
        var fakeRequest = new FlightsControllerCreateRequest(null, null, null, null, null, null, null, null);
        var sut = new FlightsService(mockWriteFlightsRepository, mockReadFlightsRepository, mockSeatsRepository);

        ServiceException serviceException = assertThrows(ServiceException.class, () -> sut.create(fakeRequest), "Expected a error here!");

        assertThat(serviceException.getErrors()).isNotEmpty();
        assertThat(serviceException.getMessage()).isEqualTo("Erro ao cadastrar um novo voo!");

        clearAndResetMocks();
    }

    @Test
    @Order(2)
    void whenCreateShouldExpectedCorrectAnswerWhenDataAreCorrect() {
        var fakeSeat = new FlightsControllerCreateRequest.Seat("01b");

        var fakeRequest = new FlightsControllerCreateRequest(
                "Azul",
                123,
                List.of(fakeSeat),
                "(CFN) Belo Horizonte, Minas Gerais",
                "(REC) Recife, Pernambuco",
                LocalDateTime.now(),
                LocalDateTime.now(),
                new BigDecimal("200.20"));

        var fakeFlightEntity = new FlightEntity();
        fakeFlightEntity.setId(UUID.randomUUID());

        doReturn(fakeFlightEntity).when(mockWriteFlightsRepository).save(any());

        var fakeSeatEntity = new SeatEntity();
        fakeSeatEntity.setId(UUID.randomUUID());

        doReturn(fakeSeatEntity).when(mockSeatsRepository).save(any());

        var sut = new FlightsService(mockWriteFlightsRepository, mockReadFlightsRepository, mockSeatsRepository);

        FlightsControllerCreateResponse response = sut.create(fakeRequest);

        assertThat(response.id()).isNotNull();

        clearAndResetMocks();
    }

    @Test
    @Order(3)
    void whenIndexShouldExpectedErrorWhenDataIsIncorrect() {
        var fakeRequest = new FlightsControllerIndexRequest(null, null);
        var sut = new FlightsService(mockWriteFlightsRepository, mockReadFlightsRepository, mockSeatsRepository);

        ServiceException serviceException = assertThrows(ServiceException.class, () -> sut.index(fakeRequest), "Expected a error here!");

        assertThat(serviceException.getErrors()).isNotEmpty();
        assertThat(serviceException.getMessage()).isEqualTo("Erro ao recuperar voos");

        clearAndResetMocks();
    }

    @Test
    @Order(4)
    void whenIndexShouldExpectedCorrectAnswerWhenDataAreCorrect() {
        var fakeRequest = new FlightsControllerIndexRequest("Bel", "rec");
        var sut = new FlightsService(mockWriteFlightsRepository, mockReadFlightsRepository, mockSeatsRepository);

        var fakeFlightId = UUID.randomUUID();

        var flightEntity = new FlightEntity();
        flightEntity.setId(fakeFlightId);

        var fakeSeatEntity = new SeatEntity();
        fakeSeatEntity.setId(UUID.randomUUID());
        fakeSeatEntity.setFlight(flightEntity);
        fakeSeatEntity.setSeatNumber("22c");

        doReturn(List.of(fakeSeatEntity))
                .when(mockSeatsRepository)
                .findAvailableSeatsByFlight(any());

        var fakeFlightEntity = new FlightEntity();
        fakeFlightEntity.setId(fakeFlightId);
        fakeFlightEntity.setCompany("Azul");
        fakeFlightEntity.setOrigin("(CFN) Belo Horizonte, Minas Gerais");
        fakeFlightEntity.setDestiny("(REC) Recife, Pernanbuco");
        fakeFlightEntity.setSeats(List.of(fakeSeatEntity));
        fakeFlightEntity.setCheckIn(LocalDateTime.now());
        fakeFlightEntity.setCheckOut(LocalDateTime.now());

        doReturn(List.of(fakeFlightEntity))
                .when(mockReadFlightsRepository)
                .findAllByParams(any());

        FlightsControllerIndexResponse response = sut.index(fakeRequest);

        assertThat(response).isNotNull();
        assertThat(response.flights()).isNotEmpty();

        clearAndResetMocks();
    }

    private void clearAndResetMocks() {
        clearInvocations(mockWriteFlightsRepository, mockReadFlightsRepository, mockSeatsRepository);
        reset(mockWriteFlightsRepository, mockReadFlightsRepository, mockSeatsRepository);
    }
}
