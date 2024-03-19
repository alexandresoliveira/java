package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.GuestModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.HotelModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.ReservationModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tbl_hotels")
public class HotelEntity extends BaseEntity implements HotelModel {

    @OneToOne(fetch = FetchType.LAZY, targetEntity = ReservationEntity.class)
    @JoinColumn(name = "reservation_id", nullable = false)
    private ReservationModel reservation;

    @Column(name = "external_id", nullable = false)
    private UUID externalId;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "room", length = 50)
    private String room;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "hotel", cascade = CascadeType.ALL, targetEntity = GuestEntity.class)
    private List<? extends GuestModel> guests;

    @Column(name = "check_in", nullable = false)
    private LocalDateTime checkIn;

    @Column(name = "check_out", nullable = false)
    private LocalDateTime checkOut;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "price", precision = 15, scale = 2, nullable = false)
    private BigDecimal price;

    @Override
    public UUID getExternalId() {
        return this.externalId;
    }

    @Override
    public ReservationModel getReservation() {
        return reservation;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getRoom() {
        return this.room;
    }

    @Override
    public List<? extends GuestModel> getGuests() {
        return this.guests;
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

    public void setExternalId(UUID externalId) {
        this.externalId = externalId;
    }

    public void setReservation(ReservationModel reservation) {
        this.reservation = reservation;
    }

    public void setGuests(List<? extends GuestModel> guests) {
        this.guests = guests;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoom(String room) {
        this.room = room;
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
