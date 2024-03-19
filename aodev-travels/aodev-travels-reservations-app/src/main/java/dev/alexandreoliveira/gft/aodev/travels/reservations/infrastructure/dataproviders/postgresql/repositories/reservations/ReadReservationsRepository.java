package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.repositories.reservations;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.ReservationModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.usecases.reservations.show.ReservationsShowRepository;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities.ReservationEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ReadReservationsRepository implements ReservationsShowRepository {

    private final JpaRepository<ReservationEntity, UUID> jpaRepository;

    public ReadReservationsRepository(@Qualifier("readJpaReservationsRepository") JpaRepository<ReservationEntity, UUID> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public ReservationModel findById(ReservationModel model) {
        return jpaRepository.findById(model.getId()).orElse(new ReservationEntity());
    }
}
