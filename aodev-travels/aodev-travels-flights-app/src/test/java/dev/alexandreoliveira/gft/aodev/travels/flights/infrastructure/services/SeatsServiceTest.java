package dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.services;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.usecases.seats.reservations.SeatsReservationsPublisher;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.entites.FlightEntity;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.entites.SeatEntity;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.repositories.SeatsRepository;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.entrypoints.subscriptions.seats.reservations.SeatsReservationsSubscriptionMessage;
import dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.services.exceptions.ServiceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SeatsServiceTest {

    private AutoCloseable closeable;

    @Mock
    SeatsRepository mockSeatsRepository;

    @Mock
    SeatsReservationsPublisher seatsReservationsPublisher;

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
    void whenReservationShouldExpectedErrorWhenDataIsIncorrect() {
        var fakeSeat = new SeatsReservationsSubscriptionMessage.Seat(
                UUID.randomUUID(),
                UUID.randomUUID(),
                null
        );
        var fakeMessage = new SeatsReservationsSubscriptionMessage(List.of(fakeSeat));

        var sut = new SeatsService(mockSeatsRepository, seatsReservationsPublisher);

        ServiceException serviceException = assertThrows(
                ServiceException.class,
                () -> sut.reservation(fakeMessage),
                "Expected a error here!");

        assertThat(serviceException.getErrors()).isNotEmpty();
        assertThat(serviceException.getMessage()).isEqualTo("Não foi possível reservar o assento.");

        clearInvocations(seatsReservationsPublisher, mockSeatsRepository);
        reset(seatsReservationsPublisher, mockSeatsRepository);
    }

    @Test
    @Order(2)
    void whenReservationShouldExpectedErrorBecauseSeatAreReserved() {
        var fakeSeat = new SeatsReservationsSubscriptionMessage.Seat(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        var fakeMessage = new SeatsReservationsSubscriptionMessage(List.of(fakeSeat));

        when(mockSeatsRepository.reservationSeats(any())).thenThrow(new RuntimeException("Error to reserve a seat. This seat has reservation."));

        var sut = new SeatsService(mockSeatsRepository, seatsReservationsPublisher);

        ServiceException serviceException = assertThrows(ServiceException.class, () -> sut.reservation(fakeMessage), "Expected a error here!");

        assertThat(serviceException.getErrors()).isNotEmpty();
        assertThat(serviceException.getErrors()).anyMatch(message -> message.equals("Error to reserve a seat. This seat has reservation."));
        assertThat(serviceException.getMessage()).isEqualTo("Não foi possível reservar o assento.");

        clearInvocations(seatsReservationsPublisher, mockSeatsRepository);
        reset(seatsReservationsPublisher, mockSeatsRepository);
    }

    @Test
    @Order(3)
    void whenReservationShouldExpectedAnswerWhenMessageAreCorrect() {
        var fakeSeat = new SeatsReservationsSubscriptionMessage.Seat(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        var fakeMessage = new SeatsReservationsSubscriptionMessage(List.of(fakeSeat));

        var flightEntity = new FlightEntity();
        flightEntity.setId(UUID.randomUUID());

        var fakeSeatEntitty = new SeatEntity();
        fakeSeatEntitty.setId(fakeSeat.seatId());
        fakeSeatEntitty.setExternalId(fakeSeat.reservationId());
        fakeSeatEntitty.setFlight(flightEntity);
        fakeSeatEntitty.setSeatNumber("21f");

        doReturn(fakeSeatEntitty)
                .when(mockSeatsRepository)
                .reservationSeats(any());

        var sut = new SeatsService(mockSeatsRepository, seatsReservationsPublisher);
        sut.reservation(fakeMessage);

        verify(seatsReservationsPublisher, times(1)).publishLockSeatEvents(any(), anyList());

        clearInvocations(seatsReservationsPublisher, mockSeatsRepository);
        reset(seatsReservationsPublisher, mockSeatsRepository);
    }

}
