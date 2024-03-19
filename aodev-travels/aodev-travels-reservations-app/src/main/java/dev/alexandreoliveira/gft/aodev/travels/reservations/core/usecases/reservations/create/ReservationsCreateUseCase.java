package dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.reservations.create;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.FlightModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.ReservationModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.IUseCase;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.utils.validators.ModelValidatorUtil;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities.FlightEntity;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities.HotelEntity;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities.TransferEntity;

import java.util.List;

public class ReservationsCreateUseCase implements IUseCase.InOut<ReservationModel, ReservationModel> {

    private final ReservationsCreatePublisher reservationsCreatePublisher;
    private final ReservationsCreateRepository repository;

    public ReservationsCreateUseCase(ReservationsCreatePublisher reservationsCreatePublisher,
                                     ReservationsCreateRepository repository) {
        this.reservationsCreatePublisher = reservationsCreatePublisher;
        this.repository = repository;
    }

    @Override
    public OutputDTO<ReservationModel> execute(ReservationModel reservationModel) {
        List<String> errors = ModelValidatorUtil.isValid(reservationModel);
        if (!errors.isEmpty()) {
            return new OutputDTO<>(errors);
        }
        ReservationModel savedReservation = repository.save(reservationModel);

        var hotel = (HotelEntity) reservationModel.getHotel();
        hotel.setReservation(savedReservation);
        repository.save(hotel);

        List<FlightModel> savedFlights = reservationModel.getFlights().stream().map(flight -> {
            var flightEntity = (FlightEntity) flight;
            flightEntity.setReservation(savedReservation);
            return repository.save(flightEntity);
        }).toList();

        reservationModel.getTransfers().forEach(transfer -> {
            var transferEntity = (TransferEntity) transfer;
            transferEntity.setReservation(savedReservation);
            repository.save(transferEntity);
        });

        reservationsCreatePublisher.lockHotels(savedReservation.getId(), savedReservation.getHotel());
        reservationsCreatePublisher.lockTransfers(savedReservation.getId(), savedReservation.getTransfers());
        reservationsCreatePublisher.lockFlights(savedReservation.getId(), savedFlights);

        return new OutputDTO<>(savedReservation);
    }
}
