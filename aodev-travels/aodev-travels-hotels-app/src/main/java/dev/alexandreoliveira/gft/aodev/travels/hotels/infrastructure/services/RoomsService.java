package dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.services;

import dev.alexandreoliveira.gft.aodev.travels.hotels.core.dtos.OutputDTO;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.models.RoomModel;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.rooms.lock.RoomsLockReservationPublisher;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.rooms.lock.RoomsLockUseCase;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.rooms.unlock.RoomsUnlockUseCase;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.dateproviders.postgresql.entites.HotelEntity;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.dateproviders.postgresql.entites.RoomEntity;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.dateproviders.postgresql.repositories.RoomsRepository;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.entrypoints.subscriptions.rooms.lock.RoomsLockSubscriptionMessage;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.entrypoints.subscriptions.rooms.unlock.RoomsUnlockSubscriptionMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RoomsService extends BaseService {

    private final RoomsLockUseCase roomsLockUseCase;
    private final RoomsUnlockUseCase roomsUnlockUseCase;

    public RoomsService(
            @Qualifier("roomsLockReservationPublisher") RoomsLockReservationPublisher roomsLockReservationPublisher,
            RoomsRepository repository) {
        this.roomsLockUseCase = new RoomsLockUseCase(repository, roomsLockReservationPublisher);
        this.roomsUnlockUseCase = new RoomsUnlockUseCase(repository);
    }

    public void lockRoom(RoomsLockSubscriptionMessage message) {
        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setId(message.hotelId());

        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setId(message.roomId());
        roomEntity.setExternalId(message.reservationId());
        roomEntity.setHotel(hotelEntity);

        OutputDTO<RoomModel> output = roomsLockUseCase.execute(roomEntity);
        hasErrors(output, "Erro ao reservar quarto");
    }

    public void unlockRoom(RoomsUnlockSubscriptionMessage message) {
        RoomEntity roomEntity = new RoomEntity();
        OutputDTO<RoomModel> output = roomsUnlockUseCase.execute(roomEntity);
        hasErrors(output, "Erro ao liberar quarto");
    }
}
