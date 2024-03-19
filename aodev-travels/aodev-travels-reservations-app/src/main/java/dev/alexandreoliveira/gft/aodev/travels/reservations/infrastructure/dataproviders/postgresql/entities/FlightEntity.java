package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.FlightModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.ReservationModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.SeatModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tbl_flights")
public class FlightEntity extends BaseEntity implements FlightModel {

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ReservationEntity.class)
    @JoinColumn(name = "reservation_id", nullable = false)
    private ReservationModel reservation;

    @Column(name = "external_id", nullable = false)
    private UUID externalId;

    @Column(name = "company", length = 100, nullable = false)
    private String company;

    @Column(name = "flight_number", nullable = false)
    private Integer flightNumber;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "flight", targetEntity = SeatEntity.class)
    private List<? extends SeatModel> seats;

    @Column(name = "origin", length = 100, nullable = false)
    private String origin;

    @Column(name = "destiny", length = 100, nullable = false)
    private String destiny;

    @Column(name = "check_in", nullable = false)
    private LocalDateTime checkIn;

    @Column(name = "check_out", nullable = false)
    private LocalDateTime checkOut;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "price", precision = 15, scale = 2, nullable = false)
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
    public String getCompany() {
        return this.company;
    }

    @Override
    public Integer getFlightNumber() {
        return this.flightNumber;
    }

    @Override
    public List<? extends SeatModel> getSeats() {
        return this.seats;
    }

    @Override
    public String getOrigin() {
        return this.origin;
    }

    @Override
    public String getDestiny() {
        return this.destiny;
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

    public void setCompany(String company) {
        this.company = company;
    }

    public void setFlightNumber(Integer flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setSeats(List<? extends SeatModel> seats) {
        this.seats = seats;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
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
