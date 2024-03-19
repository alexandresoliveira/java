package dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities;

import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.GuestModel;
import dev.alexandreoliveira.gft.aodev.travels.reservations.core.models.HotelModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_guests")
public class GuestEntity extends BaseEntity implements GuestModel {

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = HotelEntity.class)
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelModel hotel;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Override
    public HotelModel getHotel() {
        return hotel;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Integer getAge() {
        return this.age;
    }

    public void setHotel(HotelModel hotel) {
        this.hotel = hotel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
