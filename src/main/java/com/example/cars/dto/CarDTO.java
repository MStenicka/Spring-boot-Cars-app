package com.example.cars.dto;

import com.example.cars.model.Model;

import java.time.LocalDate;

public class CarDTO {

    private Long modelId;
    private String color;
    private int power;
    private double consumption;
    private LocalDate creationDate;
    private boolean isDrivable;

    public CarDTO() {
    }

    public CarDTO(Long modelId, String color, int power, double consumption, LocalDate creationDate, boolean isDrivable) {
        this.modelId = modelId;
        this.color = color;
        this.power = power;
        this.consumption = consumption;
        this.creationDate = creationDate;
        this.isDrivable = isDrivable;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isDrivable() {
        return isDrivable;
    }

    public void setDrivable(boolean drivable) {
        isDrivable = drivable;
    }


    @Override
    public String toString() {
        return "CarDTO{" +
                "modelId=" + modelId +
                ", color='" + color + '\'' +
                ", power=" + power +
                ", consumption=" + consumption +
                ", creationDate=" + creationDate +
                ", isDrivable=" + isDrivable +
                '}';
    }
}
