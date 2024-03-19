package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.FlightModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.HotelModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.ReservationModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.TransferModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tbl_reservations")
public class ReservationEntity extends BaseEntity implements ReservationModel {

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "user_name", length = 100, nullable = false)
    private String userName;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "reservation", targetEntity = HotelEntity.class)
    private HotelModel hotel;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "reservation", targetEntity = TransferEntity.class)
    private List<? extends TransferModel> transfers;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "reservation", targetEntity = FlightEntity.class)
    private List<? extends FlightModel> flights;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "price", precision = 15, scale = 2, nullable = false)
    private BigDecimal price;

    @Override
    public UUID getUserId() {
        return this.userId;
    }

    @Override
    public String getUserName() {
        return this.userName;
    }

    @Override
    public HotelModel getHotel() {
        return this.hotel;
    }

    @Override
    public List<? extends TransferModel> getTransfers() {
        return this.transfers;
    }

    @Override
    public List<? extends FlightModel> getFlights() {
        return this.flights;
    }

    @Override
    public Boolean getStatus() {
        return this.status;
    }

    @Override
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setHotel(HotelEntity hotel) {
        this.hotel = hotel;
    }

    public void setTransfers(List<TransferEntity> transfers) {
        this.transfers = transfers;
    }

    public void setFlights(List<FlightEntity> flights) {
        this.flights = flights;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
