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
    private int doorNumber;
    @Column(name = "year_of_manufacturer")
    private int yearOfManufacturer;

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

    public int getDoor_number() {
        return doorNumber;
    }

    public void setDoor_number(int door_number) {
        this.doorNumber = door_number;
    }

    public int getYearOfManufacturer() {
        return yearOfManufacturer;
    }

    public void setYearOfManufacturer(int manufacturer_year) {
        this.yearOfManufacturer = manufacturer_year;
    }
}
