package com.example.cars.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table (name = "Model")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id", referencedColumnName = "id")
    private Manufacturer manufacturer;

    @Column(name = "name")
    @NotBlank(message = "Name must not be blank")
    private String name;

    @Column(name = "category")
    @NotBlank(message = "Category must not be blank")
    private String category;

    @Column(name = "price_range")
    @NotBlank(message = "Price range must not be blank")
    private String priceRange;

    @Column(name = "release_year")
    @Min(1900)
    private int releaseYear;


    @Column(name = "is_active")
    private boolean isActive;

    public Model() {
    }

    public Model(Long id, Manufacturer manufacturer, String name, String category, String priceRange, int releaseYear, boolean isActive) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.name = name;
        this.category = category;
        this.priceRange = priceRange;
        this.releaseYear = releaseYear;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Model{" +
                "manufacturer=" + manufacturer +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", priceRange='" + priceRange + '\'' +
                ", releaseYear=" + releaseYear +
                ", isActive=" + isActive +
                '}';
    }
}
