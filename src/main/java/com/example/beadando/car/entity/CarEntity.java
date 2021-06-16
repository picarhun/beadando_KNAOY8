package com.example.beadando.car.entity;

import com.example.beadando.core.entity.CoreEntity;
import com.example.beadando.manufacturer.entity.ManufacturerEntity;

import javax.persistence.*;

@Table(name = "car")
@Entity
public class CarEntity extends CoreEntity {

    @Column(name = "Type")
    private String type;
    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private ManufacturerEntity manufacturer;
    @Column(name = "door_number")
    private Integer doorNumber;
    @Column(name = "year_of_manufacturer")
    private Integer yearOfManufacturer;

    public CarEntity() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ManufacturerEntity getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerEntity manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(Integer door_number) {
        this.doorNumber = door_number;
    }

    public Integer getYearOfManufacturer() {
        return yearOfManufacturer;
    }

    public void setYearOfManufacturer(Integer yearOfManufacturer) {
        this.yearOfManufacturer = yearOfManufacturer;
    }
}
