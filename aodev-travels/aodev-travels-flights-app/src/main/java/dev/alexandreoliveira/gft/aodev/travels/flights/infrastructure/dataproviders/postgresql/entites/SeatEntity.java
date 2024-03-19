package dev.alexandreoliveira.gft.aodev.travels.flights.infrastructure.dataproviders.postgresql.entites;

import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.FlightModel;
import dev.alexandreoliveira.gft.aodev.travels.flights.core.models.SeatModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "tbl_seats")
public class SeatEntity extends BaseEntity implements SeatModel {

    @ManyToOne(targetEntity = FlightEntity.class)
    @JoinColumn(name = "flight_id", nullable = false)
    private FlightModel flight;

    @Column(name = "external_id")
    private UUID externalId;

    @Column(name = "seat_number", length = 3, nullable = false)
    private String seatNumber;

    @Override
    public FlightModel getFlight() {
        return this.flight;
    }

    @Override
    public UUID getExternalId() {
        return this.externalId;
    }

    @Override
    public String getSeatNumber() {
        return this.seatNumber;
    }

    public void setFlight(FlightModel flight) {
        this.flight = flight;
    }

    public void setExternalId(UUID externalId) {
        this.externalId = externalId;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}
