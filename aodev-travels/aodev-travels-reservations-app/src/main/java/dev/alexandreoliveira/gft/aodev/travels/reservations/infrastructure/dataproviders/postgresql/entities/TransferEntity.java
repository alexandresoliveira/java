package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.ReservationModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.TransferModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tbl_transfers")
public class TransferEntity extends BaseEntity implements TransferModel {

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ReservationEntity.class)
    @JoinColumn(name = "reservation_id", nullable = false)
    private ReservationModel reservation;

    @Column(name = "external_id", nullable = false)
    private UUID externalId;

    @Column(name = "car_info", length = 100, nullable = false)
    private String carInfo;

    @Column(name = "check_in", nullable = false)
    private LocalDateTime checkIn;

    @Column(name = "check_out", nullable = false)
    private LocalDateTime checkOut;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "price", nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Override
    public ReservationModel getReservation() {
        return this.reservation;
    }

    @Override
    public UUID getExternalId() {
        return this.externalId;
    }

    @Override
    public String getCarInfo() {
        return this.carInfo;
    }

    @Override
    public LocalDateTime getCheckIn() {
        return this.checkIn;
    }

    @Override
    public LocalDateTime getCheckOut() {
        return this.checkOut;
    }

    @Override
    public Boolean getStatus() {
        return this.status;
    }

    @Override
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setReservation(ReservationModel reservation) {
        this.reservation = reservation;
    }

    public void setExternalId(UUID externalId) {
        this.externalId = externalId;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
