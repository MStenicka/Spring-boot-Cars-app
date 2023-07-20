package com.example.cars.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    //@NotNull(message = "Model must not be null")
    private Model model;

    @Column(name = "color")
    @NotBlank(message = "Color must not be blank")
    private String color;

    @Column(name = "power")
    @Min(value = 1, message = "Power must be at least {value}")
    @Max(value = 2000, message = "Power must not exceed {value}")
    private int power;

    @Column(name = "consumption")
    @PositiveOrZero(message = "Consumption must be non-negative")
    private double consumption;

    @Column(name = "creationDate")
    @NotNull(message = "Creation date must not be null")
    private LocalDate creationDate;

    @Column(name = "is_drivable")
    private boolean isDrivable;

    public Car() {
    }

    public Car(Long id, Model model, String color, int power, double consumption, LocalDate creationDate, boolean isDrivable) {
        this.id = id;
        this.model = model;
        this.color = color;
        this.power = power;
        this.consumption = consumption;
        this.creationDate = creationDate;
        this.isDrivable = isDrivable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
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
        return "Car{" +
                "id=" + id +
                ", model=" + model +
                ", color='" + color + '\'' +
                ", power=" + power +
                ", consumption=" + consumption +
                ", creationDate=" + creationDate +
                ", isDrivable=" + isDrivable +
                '}';
    }
}
