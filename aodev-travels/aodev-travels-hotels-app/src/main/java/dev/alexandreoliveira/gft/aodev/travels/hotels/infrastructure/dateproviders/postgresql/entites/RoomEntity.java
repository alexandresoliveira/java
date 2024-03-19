package dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.dateproviders.postgresql.entites;

import dev.alexandreoliveira.gft.aodev.travels.hotels.core.models.HotelModel;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.models.RoomModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tbl_rooms")
public class RoomEntity extends BaseEntity implements RoomModel {

    @ManyToOne(targetEntity = HotelEntity.class)
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelModel hotel;

    @Column(name = "external_id")
    private UUID externalId;

    @Column(name = "type", length = 10)
    private String type;

    @Column(name = "beds", length = 50)
    private String beds;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "price", nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Override
    public HotelModel getHotel() {
        return this.hotel;
    }

    @Override
    public UUID getExternalId() {
        return this.externalId;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public String getBeds() {
        return this.beds;
    }

    @Override
    public Boolean isAvailable() {
        return this.isAvailable;
    }

    @Override
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setHotel(HotelModel hotel) {
        this.hotel = hotel;
    }

    public void setExternalId(UUID externalId) {
        this.externalId = externalId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBeds(String beds) {
        this.beds = beds;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
