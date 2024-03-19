package dev.alexandreoliveira.gft.travels.rentcar.infrastructure.dateproviders.postgresql.entites;

import dev.alexandreoliveira.gft.travels.rentcar.core.models.TransferModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tbl_transfers")
public class TransferEntity extends BaseEntity implements TransferModel {

    @Column(name = "external_id")
    private UUID externalId;

    @Column(name = "car_info", length = 100, nullable = false)
    private String carInfo;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "price", nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Override
    public UUID getExternalId() {
        return this.externalId;
    }

    @Override
    public String getCarInfo() {
        return this.carInfo;
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

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
