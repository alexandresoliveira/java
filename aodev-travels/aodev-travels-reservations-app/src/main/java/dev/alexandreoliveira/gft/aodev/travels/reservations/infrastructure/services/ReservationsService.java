package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.services;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.ReservationModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.reservations.create.ReservationsCreateUseCase;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.reservations.locks.flights.ReservationsLocksFlightsUseCase;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.reservations.locks.hotels.ReservationsLocksHotelsUseCase;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.reservations.locks.transfers.ReservationsLocksTransfersUseCase;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.reservations.show.ReservationsShowUseCase;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities.FlightEntity;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities.GuestEntity;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities.HotelEntity;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities.ReservationEntity;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities.SeatEntity;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities.TransferEntity;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.repositories.reservations.ReadReservationsRepository;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.repositories.reservations.WriteReservationsRepository;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.reservations.create.ReservationsCreateControllerRequest;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.reservations.create.ReservationsCreateControllerResponse;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.rest.reservations.show.ReservationsControllerShowResponse;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.subscriptions.kafka.reservations.locks.flights.ReservationsLocksFlightsSubscriptionMessage;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.subscriptions.kafka.reservations.locks.hotels.ReservationsLocksHotelsSubscriptionMessage;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.entrypoints.subscriptions.kafka.reservations.locks.transfers.ReservationsLocksTransfersSubscriptionMessage;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.events.kafka.reservations.ReservationsLocksPublishersImpl;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationsService extends BaseService {

    private final ReservationsCreateUseCase reservationsCreateUseCase;
    private final ReservationsLocksHotelsUseCase reservationsLocksHotelsUseCase;
    private final ReservationsLocksFlightsUseCase reservationsLocksFlightsUseCase;
    private final ReservationsLocksTransfersUseCase reservationsLocksTransfersUseCase;
    private final ReservationsShowUseCase reservationsShowUseCase;

    public ReservationsService(
            KafkaTemplate<String, Object> kafkaTemplate,
            WriteReservationsRepository writeReservationsRepository,
            ReadReservationsRepository readReservationsRepository) {
        this.reservationsCreateUseCase = new ReservationsCreateUseCase(new ReservationsLocksPublishersImpl(kafkaTemplate), writeReservationsRepository);
        this.reservationsLocksHotelsUseCase = new ReservationsLocksHotelsUseCase(writeReservationsRepository);
        this.reservationsLocksFlightsUseCase = new ReservationsLocksFlightsUseCase(writeReservationsRepository);
        this.reservationsLocksTransfersUseCase = new ReservationsLocksTransfersUseCase(writeReservationsRepository);
        this.reservationsShowUseCase = new ReservationsShowUseCase(readReservationsRepository);
    }

    public ReservationsCreateControllerResponse create(ReservationsCreateControllerRequest request) {
        List<BigDecimal> prices = new ArrayList<>();

        List<GuestEntity> guests = request.hotel().guests().stream().map(g -> {
            var guest = new GuestEntity();
            guest.setName(g.name());
            guest.setAge(g.age());
            return guest;
        }).toList();

        var hotel = new HotelEntity();
        hotel.setExternalId(request.hotel().externalId());
        hotel.setName(request.hotel().name());
        hotel.setRoom(request.hotel().roomId().toString());
        hotel.setGuests(guests);
        hotel.setCheckIn(request.hotel().checkIn());
        hotel.setCheckOut(request.hotel().checkOut());
        hotel.setPrice(request.hotel().price());

        prices.add(request.hotel().price());

        List<TransferEntity> transfers = request.transfers().stream().map(t -> {
            var transfer = new TransferEntity();
            transfer.setExternalId(t.externalId());
            transfer.setCarInfo(t.carInfo());
            transfer.setCheckIn(t.checkIn());
            transfer.setCheckOut(t.checkOut());
            transfer.setPrice(t.price());
            prices.add(t.price());
            return transfer;
        }).toList();

        List<FlightEntity> flights = request.flights().stream().map(f -> {
            List<SeatEntity> seats = f.seats().stream().map(s -> {
                var seat = new SeatEntity();
                seat.setExternalId(s.externalId());
                seat.setSeatNumber(s.seatNumber());
                return seat;
            }).toList();

            var flight = new FlightEntity();
            flight.setExternalId(f.externalId());
            flight.setCompany(f.company());
            flight.setFlightNumber(f.flightNumber());
            flight.setSeats(seats);
            flight.setOrigin(f.origin());
            flight.setDestiny(f.destiny());
            flight.setCheckIn(f.checkIn());
            flight.setCheckOut(f.checkOut());
            flight.setPrice(f.price());
            prices.add(f.price());
            return flight;
        }).toList();

        var reservation = new ReservationEntity();
        reservation.setUserId(request.userId());
        reservation.setUserName(request.userName());
        reservation.setHotel(hotel);
        reservation.setTransfers(transfers);
        reservation.setFlights(flights);
        reservation.setPrice(prices.stream().reduce(BigDecimal::add).get());

        OutputDTO<ReservationModel> output = reservationsCreateUseCase.execute(reservation);
        hasErrors(output, "Erro ao efetuar a reserva");
        return new ReservationsCreateControllerResponse(output.data().getId(), output.data().getStatus());
    }

    public void updateHotelsLockReservation(ReservationsLocksHotelsSubscriptionMessage message) {
        var hotelEntity = new HotelEntity();
        hotelEntity.setExternalId(message.hotelId());
        hotelEntity.setRoom(message.roomId().toString());

        var reservationEntity = new ReservationEntity();
        reservationEntity.setId(message.reservationId());
        reservationEntity.setHotel(hotelEntity);

        reservationsLocksHotelsUseCase.execute(reservationEntity);
    }

    public void updateFlightsLockReservation(ReservationsLocksFlightsSubscriptionMessage message) {
        message.reservations().forEach(model -> {
            var seatEntity = new SeatEntity();
            seatEntity.setExternalId(model.seatId());

            var flight = new FlightEntity();
            flight.setExternalId(model.flightId());
            flight.setSeats(List.of(seatEntity));

            var reservationEntity = new ReservationEntity();
            reservationEntity.setId(model.reservationId());
            reservationEntity.setFlights(List.of(flight));

            reservationsLocksFlightsUseCase.execute(reservationEntity);
        });
    }

    public void updateTransfersLockReservation(ReservationsLocksTransfersSubscriptionMessage message) {
        message.reservedTransfers().forEach(model -> {
            var transfer = new TransferEntity();
            transfer.setExternalId(model.transferId());

            var reservationEntity = new ReservationEntity();
            reservationEntity.setId(model.reservationId());
            reservationEntity.setTransfers(List.of(transfer));

            reservationsLocksTransfersUseCase.execute(reservationEntity);
        });
    }

    public ReservationsControllerShowResponse show(UUID id) {
        var entity = new ReservationEntity();
        entity.setId(id);
        OutputDTO<ReservationModel> output = reservationsShowUseCase.execute(entity);
        hasErrors(output, "Error to get reservation");

        ReservationModel reservationModel = output.data();

        List<ReservationsControllerShowResponse.Guest> guests = reservationModel.getHotel().getGuests().stream().map(g ->
                new ReservationsControllerShowResponse.Guest(
                        g.getId(),
                        g.getName(),
                        g.getAge()
                )
        ).toList();

        var hotel = new ReservationsControllerShowResponse.Hotel(
                reservationModel.getHotel().getId(),
                reservationModel.getHotel().getExternalId(),
                reservationModel.getHotel().getName(),
                UUID.fromString(reservationModel.getHotel().getRoom()),
                guests,
                reservationModel.getHotel().getCheckIn(),
                reservationModel.getHotel().getCheckOut(),
                reservationModel.getHotel().getStatus(),
                reservationModel.getHotel().getPrice()
        );

        List<ReservationsControllerShowResponse.Flight> flights = reservationModel.getFlights().stream().map(f -> {
            List<ReservationsControllerShowResponse.Seat> seats = f.getSeats().stream().map(s -> new ReservationsControllerShowResponse.Seat(s.getId(), s.getExternalId(), s.getSeatNumber())).toList();
            return new ReservationsControllerShowResponse.Flight(f.getId(), f.getExternalId(), f.getCompany(), f.getFlightNumber(), seats, f.getOrigin(), f.getDestiny(), f.getCheckIn(), f.getCheckOut(), f.getStatus(), f.getPrice());
        }).toList();

        List<ReservationsControllerShowResponse.Transfer> transfers = reservationModel.getTransfers().stream().map(t ->
                new ReservationsControllerShowResponse.Transfer(t.getId(), t.getExternalId(), t.getCarInfo(), t.getCheckIn(), t.getCheckOut(), t.getStatus(), t.getPrice())
        ).toList();

        return new ReservationsControllerShowResponse(reservationModel.getId(), reservationModel.getUserId(), reservationModel.getUserName(), hotel, flights, transfers, reservationModel.getStatus(), reservationModel.getPrice());
    }
}
