package dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.dateproviders.postgresql.repositories;

import dev.alexandreoliveira.gft.aodev.travels.hotels.core.models.RoomModel;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.rooms.create.RoomsCreateRepository;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.rooms.lock.RoomsLockRepository;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.usecases.rooms.unlock.RoomsUnlockRepository;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.dateproviders.exceptions.DataProvidersException;
import dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.dateproviders.postgresql.entites.RoomEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RoomsRepository implements RoomsLockRepository, RoomsUnlockRepository, RoomsCreateRepository {

    private final JpaRepository<RoomEntity, UUID> jpaRepository;

    public RoomsRepository(@Qualifier("jpaRoomsRepository") JpaRepository<RoomEntity, UUID> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public RoomModel lock(UUID roomId, UUID hotelId, UUID reservationId, Boolean available) {
        Optional<RoomEntity> optionalRoomEntity = jpaRepository.findById(roomId);
        if (optionalRoomEntity.isEmpty()) {
            throw new DataProvidersException("Room not found for reservation.");
        }
        RoomEntity roomEntity = optionalRoomEntity.get();
        if (!roomEntity.getHotel().getId().equals(hotelId)) {
            throw new DataProvidersException("Room not found for this hotels.");
        }
        roomEntity.setExternalId(reservationId);
        roomEntity.setAvailable(available);
        return jpaRepository.save(roomEntity);
    }

    @Override
    public RoomModel unlockRoom(UUID roomId, UUID hotelId, UUID reservationId, Boolean available) {
        Optional<RoomEntity> optionalRoomEntity = jpaRepository.findById(roomId);
        if (optionalRoomEntity.isEmpty()) {
            throw new DataProvidersException("Room not found for reservation.");
        }
        RoomEntity roomEntity = optionalRoomEntity.get();
        if (!roomEntity.getHotel().getId().equals(hotelId)) {
            throw new DataProvidersException("Room not found for this hotels.");
        }
        roomEntity.setExternalId(reservationId);
        roomEntity.setAvailable(available);
        return jpaRepository.save(roomEntity);
    }

    @Override
    public RoomModel save(RoomModel roomModel) {
        var entity = (RoomEntity) roomModel;
        return jpaRepository.save(entity);
    }
}
