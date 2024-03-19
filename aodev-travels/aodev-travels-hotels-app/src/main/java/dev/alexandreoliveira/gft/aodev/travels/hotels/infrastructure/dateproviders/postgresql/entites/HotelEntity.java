package dev.alexandreoliveira.gft.aodev.travels.hotels.infrastructure.dateproviders.postgresql.entites;

import dev.alexandreoliveira.gft.aodev.travels.hotels.core.models.HotelModel;
import dev.alexandreoliveira.gft.aodev.travels.hotels.core.models.RoomModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "tbl_hotels")
public class HotelEntity extends BaseEntity implements HotelModel {

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 100)
    private String state;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "hotels", cascade = CascadeType.ALL, targetEntity = RoomEntity.class)
    private List<? extends RoomModel> rooms;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getCity() {
        return this.city;
    }

    @Override
    public String getState() {
        return this.state;
    }

    @Override
    public List<? extends RoomModel> getRooms() {
        return this.rooms;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setRooms(List<? extends RoomModel> rooms) {
        this.rooms = rooms;
    }
}
